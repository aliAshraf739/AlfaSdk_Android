package com.example.alfasdk.Const;


/**
 * Developed by Hasham.Tahir on 1/27/2016.
 */
public class Constants {
    public static final String ForgotPasswordRequest = "FGPR";
    public static final String ForgotPasswordResponse = "FGPS";
    public static final String SIGNUP_MESSAGE_IDENTIFIER = "SGNR";
    public static final String SIGNUP_MESSAGE_RESPONSE = "SGNS";
    public static final String PORTFOLIO_CASH_REQUEST_RESPONSE ="PCRS";

//    public static final String LOGIN_MESSAGE_IDENTIFIER = "LOGN";
    public static final String LOGIN_MESSAGE_IDENTIFIER = "CLGN";
    public static final String LOGIN_MESSAGE_RESPONSE = "LCNF";

    public static final String LOGOUT_MESSAGE_RESPONSE = "LOGT";
    public static final String SECOND_LEVEL_PASSWORD_REQUEST = "GSLP";
    public static final String SECOND_LEVEL_PASSWORD_RESPONSE = "GSLR";

    public static final String PORTFOLIO_CASH_REQUEST_IDENTIFIER ="PCRQ";


    public static final String SYMBOL_MESSAGE_IDENTIFIER = "SYMR";
    public static final String SYMBOL_MESSAGE_RESPONSE = "SYMS";

    public static final String SUBSCRIPTION_LIST_REQUEST_IDENTIFIER = "SLRQ";
    public static final String SUBSCRIPTION_LIST_REQUEST_RESPONSE = "SLRS";

    public static final String ORDER_STATUS_REQUEST_IDENTIFIER = "OSRQ";
    public static final String ORDER_STATUS_REQUEST_RESPONSE = "OSRS";

    public static final String SUBSCRIPTION_REQUEST_IDENTIFIER = "SREQ";
    public static final String SUBSCRIPTION_REQUEST_RESPONSE = "SRES";

    public static final String OUOTES_SECURITIES_REQUEST_IDENTIFIER = "SDTR";
    public static final String OUOTES_SECURITIES_REQUEST_RESPONSE = "SDTS";

    public static final String TOP_SYMBOLS_REQUEST_IDENTIFIER = "TSRQ";
    public static final String TOP_SYMBOLS_REQUEST_RESPONSE = "TSRS";

    public static final String PORTFOLIO_REQUEST_IDENTIFIER = "PREQ";
    public static final String PORTFOLIO_REQUEST_RESPONSE = "PRES";

    public static final String MARGIN_REQUEST_IDENTIFIER = "MREQ";
    public static final String MARGIN_REQUEST_RESPONSE = "MRES";

    public static final String LINKS_REQUEST_IDENTIFIER = "WBLR";
    public static final String LINKS_REQUEST_RESPONSE = "WBLS";

    public static final String CHANGE_PASSWORD_REQ_IDENTIFIER = "CPRQ";
    public static final String CHANGE_PASSWORD_REQ_RESPONSE = "CPRS";


    public static final String FEED_LOGIN_MESSAGE_IDENTIFIER = "FLGN";
    public static final String LOGOUT_MESSAGE_REQUEST_IDENTIFIER = "LOGR";
    public static final String ORDER_REQUEST_IDENTIFIER = "ORDR";

    public static final String ACTION_FROM_SYMBOLS = "fromSymbols";
//    public static final String ACTION_FROM_TRADE = "fromTrade";


    public static final String CHANGE_PIN_REQ_IDENTIFIER = "CPNQ";
    public static final String CHANGE_PIN_REQ_RESPONSE = "CPNS";

    public static final String TRADE_CONFIRMATION_MESG = "TCNF";

    public static final String PAYMENT_REQ_IDENTIFIER1 = "WDCR";
    public static final String PAYMENT_REQ_RESPONSE1 = "WDCS";
    public static final String PAYMENT_REQ_IDENTIFIER = "PMTR";
    public static final String PAYMENT_REQ_RESPONSE = "PMTS";

    public static final String MARGIN_DETAIL_REQ_IDENTIFIER = "RMSQ";
    public static final String MARGIN_DETAIL_REQ_RESPONSE = "RMSS";

    public static final String PROFILE_REQ_IDENTIFIER = "CPFR";
    public static final String PROFILE_REQ_RESPONSE = "CPFS";

    public static final String EXC_REQ_IDENTIFIER = "EXIR";
    public static final String EXC_REQ_RESPONSE = "EXIS";

    public static final String ACTION_ORDER_FROM_TRADE = "orderFromTrade";
    public static final String MSG_TYPE_TEXT = "TEXT";

    public static final String MARKET_STAT_RESPONSE = "MKRS";

    public static final String CHARTS_REQ_IDENTIFIER = "SGRQ";
    public static final String CHARTS_REQ_RESPONSE = "SGRS";

    public static final String NET_CUSTODY_REQ_IDENTIFIER= "NCSR";
    public static final String NET_CUSTODY_REQ_RESPONSE= "NCSS";

    public static final String CASHBOOK_REQ_IDENTIFIER = "CASR";
    public static final String CASHBOOK_REQ_RESPONSE = "CASS";

    public static final String FEED_SERVER_BROADCAST = "FEED_SERVER_BROADCAST";
    public static final String MSG_SERVER_BROADCAST = "MSG_SERVER_BROADCAST";

    //local
//    public static final String ACCOUNT_OPENING_BASE_URL = "http://192.168.0.211:9000/";

    //live
    public static final String ACCOUNT_OPENING_BASE_URL = "http://203.101.171.179:8080/";


    //Live
    public static String[] serverIpAddress = { "203.101.171.179", "203.101.171.179 "};
    public static int[] ports = {5678};

    //Local
//    public static String[] serverIpAddress = { "192.168.0.188", "192.168.0.188"};
//    public static int[] ports = {5678};


    public static final String KASB_API_LOGIN = "";
    public  static final String RESEARCH_PORTAL_URL="http://www.e-falah.com/api/v2/service/authenticate";
    public  static final String RESESRCH_PORTAL_CLIENT="atif.khan";
    public  static final String RESESRCH_PORTAL_IP="203.17577.130";
    public  static  String LOGIN_RESPONSE="";
    public  static  String SYMBOL_RESPONSE="";
    public  static  String MARKET_RESPONSE="";

    public static boolean shouldAddressCheckBoxVisible = false;

    public static boolean isCnicFrontDocProvided = true;
    public static boolean isCnicBackDocProvided = true;
    public static boolean isSignatureDocProvided = true;

}
