package com.example.lmsserver.klase.enumi;


// Ovo je sve za monitoring takodje, status se u bazi menja kroz API (PUT/POST requestove)
public enum StatusPolaganja
{
    PRIJAVLJEN,
    NIJE_PRIJAVLJEN,
    IZASAO,
    NIJE_IZASAO,
    PREDAO,
    PREGLEDAN
}
