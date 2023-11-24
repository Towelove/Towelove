package blossom.project.towelove.auth.strategy;

public enum USER_TYPE{
        PHONE("1"),EMAIL("2"),WEIXIN("3"),QQ("4");
        public String type;

        USER_TYPE(String type) {
            this.type = type;
        }
        public String getType(){
            return this.type;
        }
    }