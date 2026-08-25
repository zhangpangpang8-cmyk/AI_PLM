package com.ruoyi.system.domain.support;

/**
 * 可接收文档文件元数据的业务对象。
 *
 * 图纸、技术文档等模块实现该接口后，可复用统一的对象存储上传和元数据回填逻辑。
 */
public interface DocumentFileMetadata
{
    void setFileName(String fileName);

    void setFilePath(String filePath);

    void setSize(Long size);

    void setFileSuffix(String fileSuffix);

    void setFileSize(String fileSize);
}
