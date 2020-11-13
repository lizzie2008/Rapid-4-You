package tech.lancelot.domain.blog;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import tech.lancelot.enums.BlogType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * @author lancelot
 */
@Entity
@Getter
@Setter
@Table(name = "blog_article")
public class Article implements Serializable {

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @ApiModelProperty(value = "主键", hidden = true)
    private String id;

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    @ApiModelProperty(value = "博客类型")
    private BlogType blogType = BlogType.NORMAL;

    @NotNull
    @Type(type = "yes_no")
    @ApiModelProperty(value = "置顶")
    private Boolean isSticky = false;

    @NotNull
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    @ApiModelProperty(value = "标题")
    private String title;

    @Column(length = 50)
    @ApiModelProperty(value = "定义图片")
    private String image;

    @Lob
    @NotNull
    @Column(nullable = false)
    @ApiModelProperty(value = "摘要")
    private String summary;

    @Lob
    @NotNull
    @Column(nullable = false)
    @ApiModelProperty(value = "markdown内容")
    private String content;

    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;

    @ApiModelProperty(value = "访问量")
    private Integer readSize = 0;

    @ApiModelProperty(value = "评论量")
    private Integer commentSize = 0;

    @ApiModelProperty(value = "点赞量")
    private Integer likeSize = 0;

    @ManyToOne
    @ApiModelProperty(value = "分类")
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @ApiModelProperty(value = "归档")
    private Archive archive;

    @ManyToMany
    @JoinTable(name = "blog_article_tag",
            joinColumns = @JoinColumn(name = "article_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    @ApiModelProperty(value = "标签")
    private Set<Tag> tags;
//
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "blog")
//    @ApiModelProperty(value = "评论")
//    private Set<Comment> comments = new HashSet<Comment>();

}
