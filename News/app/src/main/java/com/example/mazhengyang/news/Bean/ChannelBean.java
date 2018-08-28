package com.example.mazhengyang.news.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mazhengyang on 18-8-28.
 */

public class ChannelBean {

    private List<Channel> channels;

    public List<Channel> getChannels() {
        return channels;
    }

    public void setChannels(List<Channel> channels) {
        this.channels = channels;
    }

    public List<Channel> getSelectedList(){
        List<Channel> list=new ArrayList<>();
        for(Channel item:channels){
            if(item.isSelected) list.add(item);
        }
        return list;
    }

    public List<Channel> getUnSelectedList(){
        List<Channel> list=new ArrayList<>();
        for(Channel item:channels){
            if(!item.isSelected) list.add(item);
        }
        return list;
    }

    public static class Channel {
        /**
         * isSelected : true
         * name : 社会
         * type : social
         */

        private boolean isSelected;
        private String name;
        private String type;

        public boolean isIsSelected() {
            return isSelected;
        }

        public void setIsSelected(boolean isSelected) {
            this.isSelected = isSelected;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
