package com.example.research_mng.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.research_mng.entity.Award;
import com.example.research_mng.entity.AwardParam;
import com.example.research_mng.entity.User;
import com.example.research_mng.mapper.AwardMapper;
import com.example.research_mng.mapper.UserMapper;
import com.example.research_mng.service.AwardService;
import com.example.research_mng.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @ClassName: ProjectServiceImpl
 * @Description: Description of the class file
 * @author: Yansomia
 * @date: 2024/7/16 15:19
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
