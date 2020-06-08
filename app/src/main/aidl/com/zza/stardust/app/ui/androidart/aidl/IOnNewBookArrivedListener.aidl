package com.zza.stardust.app.ui.androidart.aidl;

import com.zza.stardust.app.ui.androidart.aidl.Book;

interface IOnNewBookArrivedListener {
    void onNewBookArrived(in Book newBook);
}
