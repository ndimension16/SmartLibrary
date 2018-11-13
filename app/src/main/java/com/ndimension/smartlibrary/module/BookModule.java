package com.ndimension.smartlibrary.module;

public class BookModule {
    private String id,name,img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key,barcode_img;

    public BookModule(String id, String name, String img, String book_content, String book_author, String book_publish_date, String book_pdf_link, String book_qr_code,String key,String barcode_img) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.book_content = book_content;
        this.book_author = book_author;
        this.book_publish_date = book_publish_date;
        this.book_pdf_link = book_pdf_link;
        this.book_qr_code = book_qr_code;
        this.key = key;
        this.barcode_img = barcode_img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getBook_content() {
        return book_content;
    }

    public void setBook_content(String book_content) {
        this.book_content = book_content;
    }

    public String getBook_author() {
        return book_author;
    }

    public void setBook_author(String book_author) {
        this.book_author = book_author;
    }

    public String getBook_publish_date() {
        return book_publish_date;
    }

    public void setBook_publish_date(String book_publish_date) {
        this.book_publish_date = book_publish_date;
    }

    public String getBook_pdf_link() {
        return book_pdf_link;
    }

    public void setBook_pdf_link(String book_pdf_link) {
        this.book_pdf_link = book_pdf_link;
    }

    public String getBook_qr_code() {
        return book_qr_code;
    }

    public void setBook_qr_code(String book_qr_code) {
        this.book_qr_code = book_qr_code;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getBarcode_img() {
        return barcode_img;
    }

    public void setBarcode_img(String barcode_img) {
        this.barcode_img = barcode_img;
    }
}
