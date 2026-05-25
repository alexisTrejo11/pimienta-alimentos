package io.github.alexistrejo11.pimienta.shared.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

@Entity
@Table(
    name = "entity_comments",
    indexes = {
      @Index(name = "idx_entity_comments_target", columnList = "target_type, target_id"),
      @Index(name = "idx_entity_comments_author_id", columnList = "author_id"),
      @Index(name = "idx_entity_comments_deleted_at", columnList = "deleted_at")
    })
public class EntityCommentJpaEntity extends BaseJpaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "target_type", nullable = false, length = 32)
  private CommentTargetType targetType;

  @Column(name = "target_id", nullable = false)
  private Long targetId;

  @Column(name = "author_id", nullable = false)
  private Long authorId;

  @Column(nullable = false, length = 4000)
  private String body;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public CommentTargetType getTargetType() {
    return targetType;
  }

  public void setTargetType(CommentTargetType targetType) {
    this.targetType = targetType;
  }

  public Long getTargetId() {
    return targetId;
  }

  public void setTargetId(Long targetId) {
    this.targetId = targetId;
  }

  public Long getAuthorId() {
    return authorId;
  }

  public void setAuthorId(Long authorId) {
    this.authorId = authorId;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }
}
