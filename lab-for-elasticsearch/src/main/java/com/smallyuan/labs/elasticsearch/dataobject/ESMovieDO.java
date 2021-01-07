package com.smallyuan.labs.elasticsearch.dataobject;

import com.smallyuan.labs.elasticsearch.common.FieldAnalyzer;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.List;

@Document(
        indexName = "movies",   // 索引名
        shards = 1,             // 默认索引分区数
        replicas = 0,           // 每个分区的副本数
        refreshInterval = "-1"  // 刷新间隔
)
public class ESMovieDO {

    @Id
    private Integer id;

    @Field(analyzer = FieldAnalyzer.IK_MAX_WORD, type = FieldType.Text)
    private String title;

    @Field(analyzer = FieldAnalyzer.IK_MAX_WORD, type = FieldType.Text)
    private String director;

    @Field(type = FieldType.Integer)
    private Integer year;

    @Field(analyzer = FieldAnalyzer.IK_MAX_WORD, type = FieldType.Keyword)
    private List<String> genres;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    @Override
    public String toString() {
        return "ESMovieDO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", director='" + director + '\'' +
                ", year=" + year +
                ", genres=" + genres +
                '}';
    }
}
