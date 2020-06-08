package com.zza.stardust.app.ui.androidart.binderpool;

interface ISecurityCenter {
    String encrypt(String content);
    String decrypt(String password);
}