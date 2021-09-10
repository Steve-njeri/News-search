
package com.stephen.newssearch;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewsSearchResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("total_hits")
    @Expose
    private Integer totalHits;
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("page_size")
    @Expose
    private Integer pageSize;
    @SerializedName("articles")
    @Expose
    private List<Article> articles = null;
    @SerializedName("user_input")
    @Expose
    private UserInput userInput;

    /**
     * No args constructor for use in serialization
     * 
     */
    public NewsSearchResponse() {
    }

    /**
     * 
     * @param totalHits
     * @param totalPages
     * @param pageSize
     * @param page
     * @param userInput
     * @param articles
     * @param status
     */
    public NewsSearchResponse(String status, Integer totalHits, Integer page, Integer totalPages, Integer pageSize, List<Article> articles, UserInput userInput) {
        super();
        this.status = status;
        this.totalHits = totalHits;
        this.page = page;
        this.totalPages = totalPages;
        this.pageSize = pageSize;
        this.articles = articles;
        this.userInput = userInput;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTotalHits() {
        return totalHits;
    }

    public void setTotalHits(Integer totalHits) {
        this.totalHits = totalHits;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public UserInput getUserInput() {
        return userInput;
    }

    public void setUserInput(UserInput userInput) {
        this.userInput = userInput;
    }

}
