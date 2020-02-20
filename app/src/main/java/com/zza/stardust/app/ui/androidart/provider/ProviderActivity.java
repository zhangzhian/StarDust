package com.zza.stardust.app.ui.androidart.provider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zza.library.base.BasePresenter;
import com.zza.library.utils.ToastUtil;
import com.zza.stardust.R;
import com.zza.stardust.app.ui.androidart.serializableParcelable.User;
import com.zza.stardust.app.ui.androidart.aidl.Book;
import com.zza.stardust.base.MActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class ProviderActivity extends MActivity {

    @BindView(R.id.bt_insert_book)
    Button btInsertBook;
    @BindView(R.id.bt_insert_user)
    Button btInsertUser;
    @BindView(R.id.bt_query_book)
    Button btQueryBook;
    @BindView(R.id.bt_query_user)
    Button btQueryUser;
    @BindView(R.id.tv_show)
    TextView tvShow;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_provider;
    }

    @OnClick({R.id.bt_insert_book, R.id.bt_insert_user, R.id.bt_query_book, R.id.bt_query_user})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_insert_book:
                insertBook();
                break;
            case R.id.bt_insert_user:
                insertUser();
                break;
            case R.id.bt_query_book:
                queryBook();
                break;
            case R.id.bt_query_user:
                queryUser();
                break;
        }
    }

    private void queryUser() {
        Uri userUri = Uri.parse("content://com.zza.book.provider/user");
        Cursor userCursor = getContentResolver().query(userUri, new String[]{"_id", "name", "sex"}, null, null, null);
        StringBuffer buffer = new StringBuffer();
        while (userCursor.moveToNext()) {
            User user = new User();
            user.userId = userCursor.getInt(0);
            user.userName = userCursor.getString(1);
            user.isMale = userCursor.getInt(2) == 1;
            buffer.append(user.toString() + "\r\n");
        }
        tvShow.setText(buffer.toString());
        userCursor.close();
    }

    private void queryBook() {
        Uri bookUri = Uri.parse("content://com.zza.book.provider/book");
        Cursor bookCursor = getContentResolver().query(bookUri, new String[]{"_id", "name"}, null, null, null);
        StringBuffer buffer = new StringBuffer();
        while (bookCursor.moveToNext()) {
            Book book = new Book();
            book.bookId = bookCursor.getInt(0);
            book.bookName = bookCursor.getString(1);
            buffer.append(book.toString() + "\r\n");
        }
        tvShow.setText(buffer.toString());
        bookCursor.close();
    }

    private void insertUser() {
        Uri userUri = Uri.parse("content://com.zza.book.provider/user");
        ContentValues values = new ContentValues();
        values.put("name", "zza");
        values.put("sex", "man");
        getContentResolver().insert(userUri, values);
        ToastUtil.show("insertUser");
    }

    private void insertBook() {
        Uri bookUri = Uri.parse("content://com.zza.book.provider/book");
        ContentValues values = new ContentValues();
        values.put("name", "程序设计的艺术");
        getContentResolver().insert(bookUri, values);
        ToastUtil.show("insertBook");
    }
}
