package com.example.jinhui.datastorage.sharepreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jinhui.datastorage.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Email: 1004260403@qq.com
 * Created by jinhui on 2018/11/21.
 */
public class SharedPreferenceActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    @BindView(R.id.editText1)
    EditText editText1;
    @BindView(R.id.button1)
    Button button1;
    @BindView(R.id.button2)
    Button button2;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_preference);
        ButterKnife.bind(this);

        // 获取sharedPreferences的对象
//        sharedPreferences = getApplicationContext().getSharedPreferences("a", Context.MODE_PRIVATE);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        // 读取数据
        String name = sharedPreferences.getString("name", null);
        int age = sharedPreferences.getInt("age", 10);
        boolean sex = sharedPreferences.getBoolean("sex", false);
        Log.e(TAG, "SharedPreferences: " + name + "--" + age + "--" + sex);
         // E/SharedPreferenceActivity: SharedPreferences: --0--false
        // E/SharedPreferenceActivity: SharedPreferences: 1--10--true
        editText1.setText(name);

    }

    @OnClick({R.id.button1, R.id.button2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button1:
                // 保存数据,并提交数据 editor.commit();被替换成apply()
                String text = editText1.getText().toString();
                if (TextUtils.isEmpty(text)){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("name", null);
                    editor.putInt("age", 0);
                    editor.putBoolean("sex", false);
                    editor.apply();
//                    Toast.makeText(this, "输入为空，不保存哦", Toast.LENGTH_SHORT).show();
                }else {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("name", text);
                    editor.putInt("age", 10);
                    editor.putBoolean("sex", true);
                    editor.apply();
                    Toast.makeText(this, "保存成功 = " +
                            text + "--" + 10 + "--" + true, Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.button2:
                // 读取数据
                String name = sharedPreferences.getString("name", null);
                if (TextUtils.isEmpty(name)){
                    Toast.makeText(this, "读取数据为空", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, "读取的数据为 = " + name, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
