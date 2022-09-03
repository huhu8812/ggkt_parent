package com.atguigu.ggkt.vod.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.ggkt.model.vod.Subject;
import com.atguigu.ggkt.vo.vod.SubjectEeVo;
import com.atguigu.ggkt.vod.listenr.SubjectListener;
import com.atguigu.ggkt.vod.mapper.SubjectMapper;
import com.atguigu.ggkt.vod.service.SubjectService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author huhu
 * @since 2022-08-26
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Autowired
    private SubjectListener subjectListener;

    @Override
    public List<Subject> selectSubjectList(Long id) {
        LambdaQueryWrapper<Subject> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(id != null, Subject:: getParentId, id);
        List<Subject> subjectList = this.list(lambdaQueryWrapper);
        subjectList = subjectList.stream().map(item -> {
            Long subjectId = item.getId();
            if (hasChildren(subjectId)) {
                item.setHasChildren(true);
            } else {
                item.setHasChildren(false);
            }
            return item;
        }).collect(Collectors.toList());

        return subjectList;
    }

    @Override
    public void exportData(HttpServletResponse response) {
        try{
        response.setContentType("application/vnd.ms-excel"); // 表示微软的Excel
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("课程分类", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename="+ fileName + ".xlsx");
        List<Subject> subjectList = this.list();
        List<SubjectEeVo> subjectEeVoList = subjectList.stream().map(item -> {
        SubjectEeVo subjectEeVo = new SubjectEeVo();
        BeanUtils.copyProperties(item, subjectEeVo);
        return subjectEeVo;
        }).collect(Collectors.toList());
            EasyExcel.write(response.getOutputStream(), SubjectEeVo.class)
                    .sheet("课程分类")
                    .doWrite(subjectEeVoList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void importData(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), SubjectEeVo.class, subjectListener)
                    .sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean hasChildren(Long id){
        LambdaQueryWrapper<Subject> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(id != null, Subject:: getParentId, id);
        int count = this.count(lambdaQueryWrapper);
        return count>0;
    }
}
