package com.project.revision.Dto;

import java.util.List;

public class TMDBMovieWrapper {
    private int total_pages ;
    private int total_results;
    private List<TMDBMovie> results ;

    public TMDBMovieWrapper() {
    }

    public TMDBMovieWrapper(int total_pages, int total_results, List<TMDBMovie> results) {
        this.total_pages = total_pages;
        this.total_results = total_results;
        this.results = results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public List<TMDBMovie> getResults() {
        return results;
    }

    public void setResults(List<TMDBMovie> results) {
        this.results = results;
    }
}
