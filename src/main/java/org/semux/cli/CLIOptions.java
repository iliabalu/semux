/**
 * Copyright (c) 2017 The Semux Developers
 *
 * Distributed under the MIT software license, see the accompanying file
 * LICENSE or https://opensource.org/licenses/mit-license.php
 */
package org.semux.cli;

public enum CLIOptions {

    HELP("help"),

    VERSION("version"),

    ACCOUNT("account"),

    CHANGE_PASSWORD("changepassword"),

    DATA_DIR("datadir"),

    COINBASE("coinbase"),

    PASSWORD("password"),

    DUMP_PRIVATE_KEY("dumpprivatekey"),

    IMPORT_PRIVATE_KEY("importprivatekey");

    private final String name;

    CLIOptions(String s) {
        name = s;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
