package com.example.jinhui.datastorage.filestore;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jinhui.datastorage.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Email: 1004260403@qq.com
 * Created by jinhui on 2018/11/21.
 * <p>
 * 文件存储
 * <p>
 * 不加权限提示:
 * System.err: java.io.FileNotFoundException: /storage/emulated/0/edit.txt (Permission denied)
 * 2018-11-21 10:52:09.720 12381-12381/com.example.jinhui.datastorage W/System.err:     at java.io.FileInputStream.open(Native Method)
 * <p>
 * getPath与getAbsolutePath路径无差别；
 */
public class FileActivity extends AppCompatActivity {

    private static final String TAG = "FileActivity";
    @BindView(R.id.bt_fileStore)
    Button btFileStore;
    @BindView(R.id.bt_fileStream)
    Button btFileStream;
    @BindView(R.id.editText1)
    EditText editText1;
    @BindView(R.id.editText2)
    EditText editText2;

    // 方式1：保存的数据
//    File file = new File("/mnt/sdcard/edit.text");
    // 方式2，推荐方式2
    String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/edit.txt";
    File file = new File(path);



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
        ButterKnife.bind(this);

        Log.e("test", "保存的文件路径: " + path);
        // getPath 下文件路径            E/test: 保存的文件路径: /storage/emulated/0/edit.txt
        // getAbsolutePath 下文件路径    E/test: 保存的文件路径: /storage/emulated/0/edit.txt

        // 读取文件
        if (file.exists()) {
            FileReader fileReader;
            try {
                fileReader = new FileReader(file);
                char chars[] = new char[1024];
                int len;
                StringBuffer stringBuffer = new StringBuffer();
                while ((len = fileReader.read(chars)) != -1) {
                    String text = new String(chars, 0, len);
                    stringBuffer.append(text);
                }
                Log.e("test", "fileReader: " + "--" + stringBuffer);
                // 设置文本
                editText1.setText(stringBuffer);
                fileReader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
//            Toast.makeText(this, "文件不存在", Toast.LENGTH_SHORT).show();
        }

        // 读取文件
        // 得到应用程序保存数据的路径
        String path = this.getFilesDir().getPath() + "/fileOut.txt";
        Log.e(TAG, "文件流 path = " + path);  // E/FileActivity: 文件流 path = /data/user/0/com.example.jinhui.datastorage/files/fileOut.txt

        File file = new File(path);
        if (file.exists()){
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                byte bytes[] = new byte[1024];
                int len = 0;
                StringBuilder stringBuffer = new StringBuilder();
                while ((len = fileInputStream.read(bytes)) != -1){
                    String string = new String(bytes, 0, len);
                    stringBuffer.append(string);
                }
                Log.e(TAG, "stringBuffer: " + stringBuffer.toString());
                editText2.setText(stringBuffer.toString());
                fileInputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            Toast.makeText(this, "文件不存在", Toast.LENGTH_SHORT).show();
        }


    }


    @OnClick({R.id.bt_fileStore, R.id.bt_fileStream})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_fileStore:  // 将文件存储到外部存储设备（sdcard）
                String text = editText1.getText().toString();
                if (TextUtils.isEmpty(text)) {
                    Toast.makeText(this, "文本为空，不保存哦", Toast.LENGTH_SHORT).show();
                } else {
                    FileWriter fileWriter = null;
                    try {
                        fileWriter = new FileWriter(file);
                        fileWriter.write(text);
                        Toast.makeText(this, "保存成功，保存内容 = " + text, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (fileWriter != null) {
                            try {
                                fileWriter.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                break;
            case R.id.bt_fileStream:  // 文件输入输出流 将文件存储到内部存储设备（内存）
                //  MODE_WORLD_READABLE 模式已经被废弃。
                // getSharedPreferences("test" , MODE_PRIVATE);
                // MODE_WORLD_READABLE模式换成 MODE_PRIVATE
                String text2 = editText2.getText().toString();
                try {
                    FileOutputStream fileOutputStream = this.openFileOutput("fileOut.txt", Context.MODE_PRIVATE);
                    fileOutputStream.write(text2.getBytes());
                    Toast.makeText(this, "保存成功， 保存的内容为 = " + text2, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(this, "保存成功， 保存的内容为 = " + text2 + "--" + "字节数" + Arrays.toString(text2.getBytes()), Toast.LENGTH_SHORT).show();
                    fileOutputStream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
