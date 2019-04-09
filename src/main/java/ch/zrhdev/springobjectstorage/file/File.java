package ch.zrhdev.springobjectstorage.file;

import org.springframework.content.commons.annotations.ContentId;
import org.springframework.content.commons.annotations.ContentLength;
import org.springframework.content.s3.Bucket;

import javax.persistence.*;
import java.util.Date;

@Entity
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private Date created = new Date();

    @Bucket
    private String bucket;

    @ContentId
    private String contentId;

    @ContentLength
    private long contentLength;

    private String mimeType = "text/plain";

    public File() {
    }

    public File(String name, Date created, String bucket, String contentId, long contentLength, String mimeType) {
        this.name = name;
        this.created = created;
        this.bucket = bucket;
        this.contentId = contentId;
        this.contentLength = contentLength;
        this.mimeType = mimeType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public long getContentLength() {
        return contentLength;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
}
