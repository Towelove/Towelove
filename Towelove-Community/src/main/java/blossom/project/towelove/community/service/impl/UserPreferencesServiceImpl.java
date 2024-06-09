package blossom.project.towelove.community.service.impl;
import blossom.project.towelove.common.exception.EntityNotFoundException;
import blossom.project.towelove.common.exception.errorcode.BaseErrorCode;
import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.community.convert.UserPreferencesConvert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import blossom.project.towelove.community.entity.UserPreferences;
import blossom.project.towelove.community.mapper.UserPreferencesMapper;
import blossom.project.towelove.community.service.UserPreferencesService;
import blossom.project.towelove.community.dto.UserPreferencesRespDTO;
import blossom.project.towelove.community.req.UserPreferencesCreateRequest;
import blossom.project.towelove.community.req.UserPreferencesPageRequest;
import blossom.project.towelove.community.req.UserPreferencesUpdateRequest;
import java.util.List;
import java.util.Objects;

/**
 * @author: ZhangBlossom
 * @date: 2024-06-09 00:08:01
 * @contact: QQ:4602197553
 * @contact: WX:zhangblossom0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * @description: 
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserPreferencesServiceImpl extends ServiceImpl<UserPreferencesMapper, UserPreferences> implements UserPreferencesService {

    private final UserPreferencesMapper userPreferencesMapper;


    @Override
    public UserPreferencesRespDTO createUserPreferences(UserPreferencesCreateRequest createRequest) {
        UserPreferences userPreferences = UserPreferencesConvert.INSTANCE.convert(createRequest);
        if (Objects.isNull(userPreferences)) {
            log.info("the userPreferences is null...");
            return null;
        }
        userPreferencesMapper.insert(userPreferences);
        UserPreferencesRespDTO respDTO = UserPreferencesConvert.INSTANCE.convert(userPreferences);
        return respDTO;
    }

    @Override
    public UserPreferencesRespDTO getUserPreferencesById(Long UserPreferencesId) {
        return null;
    }

    @Override
    public UserPreferencesRespDTO getUserPreferencesDetailById(Long userPreferencesId) {
        UserPreferences userPreferences = userPreferencesMapper.selectById(userPreferencesId);
        if (Objects.isNull(userPreferences)) {
            log.info("the userPreferences is null...");
            return null;
        }
        UserPreferencesRespDTO respDTO = UserPreferencesConvert.INSTANCE.convert(userPreferences);
        return respDTO;
    }

    @Override
    public PageResponse<UserPreferencesRespDTO> pageQueryUserPreferences(UserPreferencesPageRequest pageRequest) {
        LambdaQueryWrapper<UserPreferences> lqw = new LambdaQueryWrapper<>();
        Page<UserPreferences> page = new Page(pageRequest.getPageNo(), pageRequest.getPageSize());
        Page<UserPreferences> userPreferencesPage = userPreferencesMapper.selectPage(page, lqw);
        List<UserPreferencesRespDTO> respDTOList = null;
        if (CollectionUtil.isEmpty(userPreferencesPage.getRecords())) {
            respDTOList = UserPreferencesConvert.INSTANCE.convert(userPreferencesPage.getRecords());
        }
        return new PageResponse<>(pageRequest.getPageNo(), pageRequest.getPageSize(), respDTOList);
    }

    @Override
    public UserPreferencesRespDTO updateUserPreferences(UserPreferencesUpdateRequest updateRequest) {
        UserPreferences userPreferences = userPreferencesMapper.selectById(updateRequest.getId());
        if (Objects.isNull(userPreferences)) {
            log.error("the album is null");
            throw new EntityNotFoundException("can not find userPreferences whick id is: " + updateRequest.getId()
                    , BaseErrorCode.ENTITY_NOT_FOUND);
        }
        try {
            userPreferencesMapper.updateById(userPreferences);
            UserPreferences resp = userPreferencesMapper.selectById(userPreferences.getUserId());
            UserPreferencesRespDTO respDTO = UserPreferencesConvert.INSTANCE.convert(resp);
            return respDTO;
        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public Boolean deleteUserPreferencesById(Long userPreferencesId) {
        return (userPreferencesMapper.deleteById(userPreferencesId)) > 0;
    }

    @Override
    public Boolean batchDeleteUserPreferences(List<Long> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            log.info("ids is null...");
            return true;
        }
        return userPreferencesMapper.deleteBatchIds(ids) > 0;
    }

}



