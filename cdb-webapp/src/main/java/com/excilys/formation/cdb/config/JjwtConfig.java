package com.excilys.formation.cdb.config;

public class JjwtConfig {
    public static final long TOKEN_LIFETIME = 604_800_000; // That's 7 days
    public static final String TOKEN_PREFIX = "User:";
    public static final String TOKEN_SECRET = "ThisIsOurSecretKeyToSignOurTokens";
}
