package com.jaramgroupware.jaramgateway.config;

import com.jaramgroupware.jaramgateway.service.ConfigService;
import com.jaramgroupware.jaramgateway.service.MajorService;
import com.jaramgroupware.jaramgateway.service.RankService;
import com.jaramgroupware.jaramgateway.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 각 enum용 mini table들의 기본값들을 어플리케이션 실행전에 미리 불러오는 컴포넌트.
 */
@Component
public class DefaultConfig {

    @Autowired
    private final ConfigService configService;

    @Autowired
    private final RoleService roleService;

    @Autowired
    private final MajorService majorService;

    @Autowired
    private final RankService rankService;

    public final String defaultRoleName;
    public final String defaultMajorName;
    public final String defaultRankName;

    public final Integer defaultRoleId;
    public final Integer defaultMajorId;
    public final Integer defaultRankId;

    public DefaultConfig(ConfigService configService, RoleService roleService, MajorService majorService, RankService rankService) {
        this.configService = configService;
        this.roleService = roleService;
        this.majorService = majorService;
        this.rankService = rankService;

        this.defaultRoleId = Integer.parseInt(configService.findConfig("default_role_pk"));
        this.defaultMajorId = Integer.parseInt(configService.findConfig("default_major_pk"));
        this.defaultRankId = Integer.parseInt(configService.findConfig("default_rank_pk"));

        this.defaultRoleName = roleService.findById(defaultRoleId);
        this.defaultMajorName = majorService.findById(defaultMajorId);
        this.defaultRankName = rankService.findById(defaultRankId);
    }

}
