/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.gui.altman;

public class PasswordInfo {
    public String password;
    public String loginPrompt = "&cPlease log in using";
    public String loginCommand = "/login %password%";
    public String serverIP;
    public MatchMode mode = MatchMode.STARTSWITH;
    public boolean ignoreColor = false;

    public PasswordInfo(String password, String serverIP) {
        this.password = password;
        this.serverIP = serverIP;
    }

    public static enum MatchMode {
        EXACT("Exact"),
        STARTSWITH("StartsWith");

        public String name;

        private MatchMode(String name) {
            this.name = name;
        }
    }
}

