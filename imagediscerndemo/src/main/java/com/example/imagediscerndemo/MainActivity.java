package com.example.imagediscerndemo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.imagediscerndemo.adapter.DiscernResultAdapter;
import com.example.imagediscerndemo.model.GetDiscernResultResponse;
import com.example.imagediscerndemo.model.GetTokenResponse;
import com.example.imagediscerndemo.network.NetCallBack;
import com.example.imagediscerndemo.network.ServiceGenerator;
import com.example.imagediscerndemo.utils.Base64Util;
import com.example.imagediscerndemo.utils.Constant;
import com.example.imagediscerndemo.utils.FileUtil;
import com.example.imagediscerndemo.utils.SPUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    /**
     * Api服务
     */
    private ApiService service;

    /**
     * 鉴权Token
     */
    private String accessToken;

    /**
     * 显示图片
     */
    private ImageView ivPicture;
    /**
     * 进度条
     */
    private ProgressBar pbLoading;

    /**
     * 底部弹窗
     */
    private MyBottomSheetDialog bottomSheetDialog;
    /**
     * 弹窗视图
     */
    private View bottomView;

    private RxPermissions rxPermissions;


    private static final int OPEN_ALBUM_CODE = 100;
    private static final int TAKE_PHOTO_CODE = 101;

    private File outputImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivPicture = findViewById(R.id.iv_picture);
        pbLoading = findViewById(R.id.pb_loading);
        rxPermissions = new RxPermissions(this);
        bottomSheetDialog = new MyBottomSheetDialog(this);
        bottomView = getLayoutInflater().inflate(R.layout.dialog_bottom, null);
        service = ServiceGenerator.createService(ApiService.class);
        requestApiGetToken();
        //獲取Token
        getAccessToken();
    }

    private void showDiscernResult(List<GetDiscernResultResponse.ResultBean> result) {
        bottomSheetDialog.setContentView(bottomView);
        bottomSheetDialog.getWindow().findViewById(com.google.android.material.R.id.design_bottom_sheet).setBackgroundColor(Color.TRANSPARENT);
//        bottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, (int) (getResources().getDisplayMetrics().heightPixels * 0.75));
        RecyclerView rvResult = bottomSheetDialog.findViewById(R.id.rv_result);
        DiscernResultAdapter adapter = new DiscernResultAdapter(R.layout.item_result_rv, result);
        rvResult.setLayoutManager(new LinearLayoutManager(this));
        rvResult.setAdapter(adapter);

        //隐藏加载
        pbLoading.setVisibility(View.GONE);
        //显示弹窗
        bottomSheetDialog.show();
    }

    /**
     * 功能描述：识别相册图片
     *
     * @param view
     * @author Tzy
     * @date 2024/01/31 11:21
     */
    @SuppressLint("CheckResult")
    public void IdentifyAlbumPictures(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(granted -> {
                if (granted) {
                    //获取图片
                    openAlbum();
                } else {
                    showMsg("权限被拒绝");
                }
            });
        } else {
            openAlbum();
        }
    }

    /**
     * 功能描述：打开相册
     *
     * @author Tzy
     * @date 2024/01/31 11:21
     */

    private void openAlbum() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, OPEN_ALBUM_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            pbLoading.setVisibility(View.VISIBLE);
            if (requestCode == OPEN_ALBUM_CODE) {
                //打开相册返回
                String[] filePathColumns = {MediaStore.Images.Media.DATA};
                final Uri imageUri = Objects.requireNonNull(data).getData();
                Cursor cursor = getContentResolver().query(imageUri, filePathColumns, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumns[0]);
                //获取图片路径
                String imagePath = cursor.getString(columnIndex);
                cursor.close();
                //识别
                localImageDiscern(imagePath);
            }else if (requestCode==TAKE_PHOTO_CODE){
                //拍照返回
                String imagePath = outputImage.getAbsolutePath();
                //識別
                localImageDiscern(imagePath);
            }else {
                showMsg("什麼都沒有");
            }
        }
    }

    private void localImageDiscern(String imagePath) {
        try {
            if (accessToken == null) {
                showMsg("获取AccessToken为null");
                return;
            }
//通过图片路径显示图片
            Glide.with(this).load(imagePath).into(ivPicture);
//按字节读取文件
            byte[] imgData = FileUtil.readFileByBytes(imagePath);
            //字節轉Base64
            String imageBase64 = Base64Util.encode(imgData);
            //圖像識別
            ImageDiscern(accessToken, imageBase64, imagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void ImageDiscern(String token, String imageBase64, String imgurl) {
        service.getDiscernResult(token, imageBase64, imgurl).enqueue(new NetCallBack<GetDiscernResultResponse>() {

            @Override
            public void onSuccess(Call<GetDiscernResultResponse> call, Response<GetDiscernResultResponse> response) {
                List<GetDiscernResultResponse.ResultBean> result = response.body() != null ? response.body().getResult() : null;
                if (result != null && result.size() > 0) {
                    //显示识别结果
                    showDiscernResult(result);
                } else {
                    pbLoading.setVisibility(View.GONE);
                    showMsg("未获得相应的识别结果");
                }
            }

            @Override
            public void onFailed(String errorStr) {
                pbLoading.setVisibility(View.GONE);
                Log.e(TAG, "图像识别失败，失败原因：" + errorStr);
            }
        });
    }


    /**
     * 识别网络图片
     *
     * @param view
     */
    public void IdentifyWebPictures(View view) {
        pbLoading.setVisibility(View.VISIBLE);
        if (accessToken == null) {
            showMsg("获取AccessToken到null");
            return;
        }
        String imgUrl = "https://t7.baidu.com/it/u=1547541832,2735498508&fm=193";
        //显示图片
        Glide.with(this).load(imgUrl).into(ivPicture);
        showMsg("图像识别中");
        service.getDiscernResult(accessToken, null, imgUrl).enqueue(new NetCallBack<GetDiscernResultResponse>() {
            @Override
            public void onSuccess(Call<GetDiscernResultResponse> call, Response<GetDiscernResultResponse> response) {
                List<GetDiscernResultResponse.ResultBean> result = response.body() != null ? response.body().getResult() : null;
                if (result != null && result.size() > 0) {
                    //显示识别结果
                    showDiscernResult(result);
                } else {
                    pbLoading.setVisibility(View.GONE);
                    showMsg("未获得相应的识别结果");
                }
            }

            @Override
            public void onFailed(String errorStr) {
                pbLoading.setVisibility(View.GONE);
                Log.e(TAG, "图像识别失败，失败原因：" + errorStr);
            }
        });
    }

    /**
     * Toast提示
     *
     * @param msg 内容
     */
    private void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void requestApiGetToken() {
        String grant_type = "client_credentials";
        String apiKey = "MPvwpvlsyE2GkObAujWygUBs";
        String apiSecret = "tHFjTiUcltHgOfQHkb1xFerMDuvO27bp";
        service.getToken(grant_type, apiKey, apiSecret)
                .enqueue(new NetCallBack<GetTokenResponse>() {
                    @Override
                    public void onSuccess(Call<GetTokenResponse> call, Response<GetTokenResponse> response) {
                        if (response.body() != null) {
                            //鉴权Token
                            accessToken = response.body().getAccess_token();
                            //過期時間 秒
                            long expiresIn = response.body().getExpires_in();
//                            當前時間
                            long currentTimeMillis = System.currentTimeMillis();
                            //放入緩存
                            SPUtils.putString(Constant.TOKEN, accessToken, MainActivity.this);
                            SPUtils.putLong(Constant.GET_TOKEN_TIME, expiresIn, MainActivity.this);
                            SPUtils.putLong(Constant.TOKEN_VALID_PERIOD, currentTimeMillis, MainActivity.this);
                            Log.d(TAG, accessToken);
                        }
                    }

                    @Override
                    public void onFailed(String errorStr) {
                        Log.e(TAG, "获取Token失败，失败原因：" + errorStr);
                        accessToken = null;
                    }
                });

    }

    private String getAccessToken() {
        String token = SPUtils.getString(Constant.TOKEN, null, this);
        if (token == null) {
            //訪問API獲取接口
            requestApiGetToken();
        } else {
            //判斷Token是否過期
            if (isTokenExpired()) {
                requestApiGetToken();
            } else {
                accessToken = token;
            }
        }
        return accessToken;
    }


    /**
     * 功能描述：判斷Token是否過期
     *
     * @return boolean
     * @author Tzy
     * @date 2024/01/30 20:58
     */

    private boolean isTokenExpired() {
        //獲取Token的時間
        long getAccessTokenTime = SPUtils.getLong(Constant.GET_TOKEN_TIME, 0, MainActivity.this);
        //Token有效時間
        long tokenValidPeriod = SPUtils.getLong(Constant.TOKEN_VALID_PERIOD, 0, MainActivity.this);
        //當前時間
        long currentTimeMillis = System.currentTimeMillis() / 1000;
        return (currentTimeMillis - getAccessTokenTime) >= tokenValidPeriod;
    }


    /**
     * 功能描述：識別拍照圖片
     *
     * @param view
     * @author Tzy
     * @date 2024/01/31 11:50
     */

    @SuppressLint("CheckResult")
    public void IdentifyTakePhotoImage(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            rxPermissions.request(
                    Manifest.permission.CAMERA
            ).subscribe(grant -> {
                if (grant) {
                    turnToCamera();
                } else {
                    showMsg("请先授权相机权限");
                }
            });
        } else {
            turnToCamera();
        }
    }

    private void turnToCamera() {
        SimpleDateFormat timeStampFormat = new SimpleDateFormat("HH_mm_ss");
        String fileName = timeStampFormat.format(new Date());
        //創建FIle對象
        outputImage = new File(getExternalCacheDir(), "takePhoto" + fileName + ".jpg");
        Uri imageUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            imageUri = FileProvider.getUriForFile(this, "com.example.imagediscerndemo.fileprovider", outputImage);
        } else {
            imageUri = Uri.fromFile(outputImage);
        }
        //打開相機
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO_CODE);
    }
}