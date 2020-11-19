package tech.lancelot.domain.blog;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author lancelot
 */

@Data
@ToString
@NoArgsConstructor
@Document(indexName = "rapid", type = "article", shards = 1, replicas = 0)
public class ArticleEs implements Serializable {
    private static final long serialVersionUID = 1L;

    @Field(index = false)
    private String id;
    @Field(type = FieldType.Text, index = true, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String title;
    @Field(type = FieldType.Text, index = true, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String summary;
    @Field(type = FieldType.Text, index = true, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String content;
    @Field(type = FieldType.Text, index = true, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String category;
    @Field(type = FieldType.Text, index = true, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String tags;
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
    private Timestamp createTime;
}
