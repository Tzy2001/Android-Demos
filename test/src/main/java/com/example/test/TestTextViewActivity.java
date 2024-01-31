package com.example.test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.example.test.Util.CustomDialog;
import com.example.test.Util.QRCodeUtil;
import com.example.test.databinding.ActivityTestTextviewBinding;
import com.gingersoft.gsa.cloud.common.utils.keyboard.KeyboardHelper;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;


/**
 * @ClassName Test
 * @Author TZY
 * @Description: 文本超鏈接，spinner下拉選項，switch開關，搜索圖標Intent跳轉到RestaurantLogoActivity，二維碼展示（可以傳入圖片改變二維碼顏色），簡單的自定義彈窗（CustomDialog），列表彈窗，單選框，多選框
 * @Date 2023/10/25 11:30
 **/
public class TestTextViewActivity extends AppCompatActivity {

    private AlertDialog alertDialog1; //信息框
    private AlertDialog alertDialog2; //单选框
    private AlertDialog alertDialog3; //多選框
    private final String Tag = "TestTextViewActivity";
    private LinearLayout ll_test;
    private TextView tv_test;
    private SwitchCompat test_switch;
    private Spinner spStyle;
    private LinearLayout llSearch;
    private ImageView ivSearch;
    private EditText edPhone;
    private ImageView ivQrcode;
    private  Button btnShowDialog;
    private  Button btnListDialog;
    private  Button btnSingleDialog;
    private  Button btnMultipleDialog;
    private CheckBox ckSystem;

    private Context mContext;
    private ActivityTestTextviewBinding binding;
    Bitmap bitmap = null;
    private String[] styles = new String[]{"style1", "style2", "style3", "style4", "style5"};
    private String[] urls = new String[]{"https://www.souhu.com", "https://www.jd.com", "https://www.taobao.com", "https://blog.csdn.net", "https://www.weibo.com"};

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityTestTextviewBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
        setContentView(R.layout.activity_test_textview);
        ll_test = findViewById(R.id.ll_test);
        tv_test = findViewById(R.id.tv_test);
        test_switch = findViewById(R.id.test_switch);
        spStyle = findViewById(R.id.sp_style);
        llSearch = findViewById(R.id.ll_search);
        edPhone = findViewById(R.id.ed_phone);
        ivSearch = findViewById(R.id.iv_search);
        ivQrcode = findViewById(R.id.iv_qrcode);
        btnShowDialog = findViewById(R.id.btn_show_dialog);
        btnListDialog = findViewById(R.id.btn_list_dialog);
        btnSingleDialog = findViewById(R.id.btn_single_dialog);
        btnMultipleDialog = findViewById(R.id.btn_multiple_dialog);
        ckSystem = findViewById(R.id.ck_system);
        mContext = this;
        textLink();
        spinner();
        initListener();
//        ll_test.setEnabled(false);

//        ll_test.setClickable(true);
//        test_switch.setEnabled(false);
//        test_switch.setClickable(false);

//        ll_test.setEnabled(false);
//        test_switch.setEnabled(false);
//        test_switch.setClickable(false);

//        ll_test.setClickable(true);
//        test_switch.setEnabled(false);

//        ll_test.setClickable(false);

//        ll_test.setClickable(true);
//        test_switch.setClickable(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    private void initListener() {
        test_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int currentApiVersion = Build.VERSION.SDK_INT;
                String Api = String.valueOf(currentApiVersion);
                Log.d(Tag, Api);
                if (b){
                    Toast.makeText(mContext, "This is Switch", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ckSystem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                String format = String.format("您%s點擊了這個CheckBox", b ? "勾選" : "未勾選");
                    Toast.makeText(TestTextViewActivity.this,format,Toast.LENGTH_SHORT).show();
            }
        });
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edPhone.getText().toString().equals("123456")) {
                    Intent intent = new Intent(mContext, RestaurantLogoActivity.class);
                    String imageUrl = "https://img.zcool.cn/community/01088658aaa5daa801219c778e5c86.png@2o.png";
                    intent.putExtra("data", imageUrl);
                    startActivity(intent);
                }
            }
        });
        btnShowDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialog();
            }
        });
        KeyboardHelper.INSTANCE.observeKeyboardHeight(this, new Function2<Integer, Integer, Unit>() {
            @Override
            public Unit invoke(Integer height, Integer integer2) {
                if (height > 0) {
                    ivQrcode.setVisibility(View.GONE);
                } else {
                    ivQrcode.setVisibility(View.VISIBLE);
                }
                return null;
            }
        });
    }

    private void spinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, styles);
        spStyle.setAdapter(adapter);
        spStyle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position) {
                    case 0:
//                        Toast.makeText(mContext, "This is Style1", Toast.LENGTH_SHORT).show();
//                        Bitmap oldBitmap = QRCodeUtil.createQRCode(urls[0], 300, 300);
//                        bitmap = QRCodeUtil.replaceBitmapColor(oldBitmap, getColor(R.color.purple_a700));
                        bitmap = QRCodeUtil.createQRCode(urls[0], getColor(R.color.purple_a700), 300, 300);

                        ivQrcode.setImageBitmap(bitmap);
                        break;
                    case 1:
//                        bitmap = QRCodeUtil.replaceBitmapColor(
//                                QRCodeUtil.createQRCode(urls[1], 300, 300),
//                                getColor(R.color.app_color_blue));
                        bitmap = QRCodeUtil.createQRCode(urls[1], getColor(R.color.app_color_blue), 300, 300);
                        ivQrcode.setImageBitmap(bitmap);
//                        Toast.makeText(mContext, "This is Style2", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
//                        bitmap = QRCodeUtil.replaceBitmapColor(
//                                QRCodeUtil.createQRCode(urls[2], 300, 300),
//                                getColor(R.color.red));
                        bitmap = QRCodeUtil.createQRCode(urls[2], getColor(R.color.red), 300, 300);
                        ivQrcode.setImageBitmap(bitmap);
//                        Toast.makeText(mContext, "This is Style3", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
//                        Toast.makeText(mContext, "This is Style4", Toast.LENGTH_SHORT).show();
//                        bitmap = QRCodeUtil.replaceBitmapColor(
//                                QRCodeUtil.createQRCode(urls[3], 300, 300),
//                                getColor(R.color.pink_a400));
                        bitmap = QRCodeUtil.createQRCode(urls[3], getColor(R.color.pink_a400), 300, 300);
                        ivQrcode.setImageBitmap(bitmap);
                        break;
                    case 4:
//                        bitmap = QRCodeUtil.replaceBitmapColor(
//                                QRCodeUtil.createQRCode(urls[4], 300, 300),
//                                getColor(R.color.yellow));
                        bitmap = QRCodeUtil.createQRCode(urls[4], getColor(R.color.yellow), 300, 300);
                        ivQrcode.setImageBitmap(bitmap);
                        //                        Toast.makeText(mContext, "This is Style5", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void textLink() {
        String text = "請點擊這裡訪問百度";
        SpannableString spannableString = new SpannableString(text);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Uri uri = Uri.parse("http://www.baidu.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setColor(getColor(R.color.purple_a700));
                ds.setUnderlineText(false);
            }
        };
        Drawable drawable=getResources().getDrawable(R.drawable.apple_pic);
        drawable.setBounds(0,0,50,50);

        ImageSpan imageSpan= new ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM);
        spannableString.setSpan(imageSpan,0,text.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(clickableSpan, 5, 9, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tv_test.setText(spannableString);
        tv_test.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void showCustomDialog(){
        CustomDialog customDialog = new CustomDialog(this);
        customDialog.setTitle(getString(R.string.dialog_title)+": 歡飲來到王者榮耀");
        customDialog.setContent(getString(R.string.dialog_content)+": 111");
        customDialog.show();
    }

    public void showList(View view){
        final String[] items={"列表1","列表2","列表3","列表4","列表5"};
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("這是列表框");
        alertBuilder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(TestTextViewActivity.this,items[i],Toast.LENGTH_SHORT).show();
                alertDialog1.dismiss();
            }
        });
        alertDialog1=alertBuilder.create();
        alertDialog1.show();
    }

    public void showSingleAlertDialog(View view){
        final String[] items={"單選1","單選2","單選3","單選4","單選5"};
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(TestTextViewActivity.this);
        alertBuilder.setTitle("這是單選框");
        alertBuilder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(TestTextViewActivity.this,items[i],Toast.LENGTH_SHORT).show();
            }
        });
        alertBuilder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog2.dismiss();
            }
        });
        alertBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog2.dismiss();
            }
        });
        alertDialog2=alertBuilder.create();
        alertDialog2.show();
    }

    public void showMultipleAlertDialog(View view){
        final String[] items={"多選1","多選2","多選3","多選4","多選5"};
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(TestTextViewActivity.this);
        alertBuilder.setTitle("這是多選框");
        alertBuilder.setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean isChecked) {
                if (isChecked){
                    Toast.makeText(TestTextViewActivity.this,"選擇"+items[i],Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(TestTextViewActivity.this,"取消選擇"+items[i],Toast.LENGTH_SHORT).show();
                }

            }
        });
        alertBuilder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog3.dismiss();
            }
        });
        alertBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog3.dismiss();
            }
        });
        alertDialog3=alertBuilder.create();
        alertDialog3.show();
    }


}