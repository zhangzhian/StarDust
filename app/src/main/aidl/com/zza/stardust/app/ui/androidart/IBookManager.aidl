package com.zza.stardust.app.ui.androidart;

import com.zza.stardust.app.ui.androidart.Book;
import com.zza.stardust.app.ui.androidart.IOnNewBookArrivedListener;

//  /build/generated/aidl_source_output_dir目录下的com.zza.stardust.app.ui.androidart包中
interface IBookManager {
     List<Book> getBookList();
     void addBook(in Book book);
     void registerListener(IOnNewBookArrivedListener listener);
     void unregisterListener(IOnNewBookArrivedListener listener);
}