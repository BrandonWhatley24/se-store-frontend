class ClientCode{
    public String getUser(){
        String username = "testUser";
        String password = "userTestPass";

        return "TEST";
    }

    webview.addJavascriptInterface(new ClientCode(), "HELLO");
}

