package com.example.sortinggame;

public class Image
{
        private String imgpath;
        private int category, isPreloaded;
        
        public Image(String path, int catName, int preloaded)
        {
                this.imgpath = path;
                this.category = catName;
                this.isPreloaded = preloaded;
        }
        public Image(String path, int catName)
        {
                this.imgpath = path;
                this.category = catName;
        }
        public Image() {
                this.imgpath = null;
                this.category = -1;
                this.isPreloaded = -1;
        }
        public void setPath(String path)
        {
                this.imgpath = path;
        }
        public String getPath()
        {
                return imgpath;
        }
        public void setCatName(int catName)
        {
                this.category = catName;
        }
        public int getCatName()
        {
                return category;
        }
        
        public int isPreloaded() {
                return isPreloaded;
        }
        public void setPreloaded(int isPreloaded) {
                this.isPreloaded = isPreloaded;
        }
        
}