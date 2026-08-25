package com.ruoyi.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.system.domain.support.DocumentFileMetadata;

/**
 * PLM 文档文件存储服务，统一对象存储上传和业务文件元数据回填。
 */
@Service
public class DocumentFileStorageService
{
    @Autowired
    private IMinioService minioService;

    @Value("${minio.bucketName}")
    private String bucketName;

    /**
     * 上传文件并将存储结果回填到业务对象。
     *
     * @param file 待上传文件
     * @param metadata 接收文件元数据的业务对象
     * @return 对象存储访问地址
     */
    public String uploadAndApply(MultipartFile file, DocumentFileMetadata metadata) throws Exception
    {
        String fileUrl = minioService.uploadFile(file, bucketName);
        metadata.setFileName(file.getOriginalFilename());
        metadata.setFilePath(fileUrl);
        metadata.setSize(file.getSize());
        metadata.setFileSuffix(FileUploadUtils.getExtension(file));
        metadata.setFileSize(FileUploadUtils.formatFileSize(file.getSize()));
        return fileUrl;
    }
}
