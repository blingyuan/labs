package com.smallyuan.labs.elasticsearch.dataobject;

import com.smallyuan.labs.elasticsearch.common.FieldAnalyzer;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 用于ES搜索
 */
@Document(
        indexName = "product",   // 索引名
        shards = 1,             // 默认索引分区数
        replicas = 0,           // 每个分区的副本数
        refreshInterval = "-1"  // 刷新间隔
)
public class ESProductDO {

    /**
     * 主键ID
     */
    @Id
    private Integer id;

    /**
     * spu名字
     */
    @Field(analyzer = FieldAnalyzer.IK_MAX_WORD, type = FieldType.Text)
    private String name;

    /**
     * 卖点
     */
    @Field(analyzer = FieldAnalyzer.IK_MAX_WORD, type = FieldType.Text)
    private String sellPoint;

    /**
     * 分类编号
     */
    private Integer cid;
    /**
     * 分类名
     */
    @Field(analyzer = FieldAnalyzer.IK_MAX_WORD, type = FieldType.Text)
    private String categoryName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSellPoint() {
        return sellPoint;
    }

    public void setSellPoint(String sellPoint) {
        this.sellPoint = sellPoint;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "ESProductDO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sellPoint='" + sellPoint + '\'' +
                ", cid=" + cid +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
