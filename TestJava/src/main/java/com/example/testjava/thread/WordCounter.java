package com.example.testjava.thread;

/**
 * @ClassName WordCounter
 * @Author TZY
 * @Date 2024/1/19 12:29
 **/

import java.util.concurrent.ConcurrentHashMap;

public class WordCounter {
    private ConcurrentHashMap<String, Integer> wordCountMap = new ConcurrentHashMap<>();

    public void processText(String text) {
        String[] words = text.split("\\s+"); // 使用空白字符分割文本

        for (String word : words) {
            // 使用 compute 方法原子地更新单词计数
            wordCountMap.compute(word, (key, oldValue) -> (oldValue == null) ? 1 : oldValue + 1);

        }

    }

    public void printWordCounts() {
        // 遍历并打印单词计数
        for (String word : wordCountMap.keySet()) {
            int count = wordCountMap.get(word);
            System.out.println(word + ": " + count);
        }
    }

    public static void main(String[] args) {
        WordCounter wordCounter = new WordCounter();

        // 模拟多线程环境，多个线程同时处理文本
        for (int i = 0; i < 10; i++) {
            final int threadIndex = i;
            new Thread(() -> {
                String text = "This is a sample text for word counting. Thread " + threadIndex;
                wordCounter.processText(text);
            }).start();
        }

        // 等待所有线程完成
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 打印单词计数结果
        wordCounter.printWordCounts();
    }
}

