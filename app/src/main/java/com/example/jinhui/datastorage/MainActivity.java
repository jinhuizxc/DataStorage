package com.example.jinhui.datastorage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.jinhui.datastorage.contentprovider.ContentProviderActivity;
import com.example.jinhui.datastorage.filestore.FileActivity;
import com.example.jinhui.datastorage.sharepreference.SharedPreferenceActivity;
import com.example.jinhui.datastorage.sqlite.SqLiteActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Android常见数据存储
 *
 * https://www.cnblogs.com/ITtangtang/p/3920916.html#type4
 *
 * http://www.cnblogs.com/univalsoft-mobile-team/p/7637340.html
 * <p>
 * 1 使用SharedPreferences存储数据
 * <p>
 * 2 文件存储数据
 * 　分为内部储存和外部存储。内部存储是应用程序使用Android为自己分配的内存空间，
 * 数据存储到“/data/data/程序包名/files”路径下的相应文件中。外部存储是使用手机sdcard的内存（这个sdcard并不是我们经常说的那个可以拆卸替换的SD卡，那个SD卡我们称之为扩展卡），使用这部分内存要声明相应的权限。　　
 * 相关连接：《Android中数据存储——文件存储数据 》　
 * <p>
 * 3 SQLite数据库存储数据
 * 　使用数据库进行存储，这个一般数据量比较大的时候。　
 * <p>
 * 4 使用ContentProvider存储数据
 * 这个比较眼熟，ContentProvider也是Android的四大组件之一。
 * ContentProvider一般是第三方提供的数据存储方式，向我们手机中的通讯录联系人，照片，音乐等……
 *
 * 实现不同应用间的数据共享就要使用到ContentProvider。
 * ContentProvider使用方法有两种：
 * 一种是使用现有的内容提供器来读取和操作相应程序中的数据；
 * 另一种是创建自己的内容提供器给我们的应用提供外部访问接口。
 *
 * <p>
 * 5 网络存储数据
 * 这个是将数据上传到网络上进行存储。
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bt_sharePreference)
    Button btSharePreference;
    @BindView(R.id.bt_file)
    Button btFile;
    @BindView(R.id.bt_sqLite)
    Button btSqLite;
    @BindView(R.id.bt_contentProvider)
    Button btContentProvider;
    @BindView(R.id.bt_internet)
    Button btInternet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_sharePreference, R.id.bt_file, R.id.bt_sqLite, R.id.bt_contentProvider, R.id.bt_internet})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_sharePreference:  // 使用SharedPreferences存储数据
                startActivity(new Intent(this, SharedPreferenceActivity.class));
                break;
            case R.id.bt_file:  // 文件存储数据
                startActivity(new Intent(this, FileActivity.class));
                break;
            case R.id.bt_sqLite:  // SQLite数据库存储数据
                startActivity(new Intent(this, SqLiteActivity.class));
                break;
            case R.id.bt_contentProvider:  // 使用ContentProvider存储数据
                startActivity(new Intent(this, ContentProviderActivity.class));
                break;
            case R.id.bt_internet:  // 网络存储数据
                Toast.makeText(this, "暂无需处理， 待更新", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
