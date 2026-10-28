package com.example.demo2.klase.enumi;


// Ovo je sve za monitoring takodje, status se u bazi menja kroz API (PUT/POST requestove)
public enum StatusPolaganja
{
    PRIJAVLJEN,
    NIJE_PRIJAVLJEN,
    IZASAO,
    NIJE_IZASAO,
    PREDAT,
    PREGLEDAN
}
