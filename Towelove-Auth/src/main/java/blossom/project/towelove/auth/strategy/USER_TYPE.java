package blossom.project.towelove.auth.strategy;

public enum USER_TYPE{
        PHONE("phone"),EMAIL("email"),WEIXIN("wx"),QQ("qq");
        public final String type;

        USER_TYPE(String type) {
            this.type = type;
        }
        public String getType(){
            return this.type;
        }
    }