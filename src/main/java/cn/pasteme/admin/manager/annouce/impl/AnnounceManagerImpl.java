package cn.pasteme.admin.manager.annouce.impl;

import cn.pasteme.admin.dto.AnnounceRequestDTO;
import cn.pasteme.admin.entity.AnnounceDO;
import cn.pasteme.admin.enumeration.AnnounceType;
import cn.pasteme.admin.manager.annouce.AnnounceManager;
import cn.pasteme.admin.mapper.AnnounceMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Acerkoo
 * @version 1.0.0
 */

@Slf4j
@Service
public class AnnounceManagerImpl implements AnnounceManager {

    @Autowired
    private AnnounceMapper announceMapper;

    @Override
    public boolean createAnnouncement(AnnounceRequestDTO node) {
        if (node.getType() > 2 || node.getType() < 0) {
            log.error("Cannot identify the type of this Announcement");
            return false;
        }
        try {
            AnnounceDO announceDO = new AnnounceDO();
            announceDO.setTitle(node.getTitle());
            announceDO.setContent(node.getContent());
            announceDO.setTime(new Date());
            announceDO.setLink(node.getLink());
            return announceMapper.createAnnouncement(announceDO, node.getType());
        } catch (Exception e) {
            log.error("create Announcement error=", e);
        }
        return false;
    }

    @Override
    public boolean deleteAnnouncement(Long id) {
        try {
            announceMapper.deleteAnnouncement(id);
            return true;
        } catch (Exception e) {
            log.error("When delete announcement, error = ", e);
        }
        return false;
    }

    @Override
    public boolean updateAnnouncement(Long id, AnnounceRequestDTO node) {
        if (node.getType() > 2 || node.getType() < 0) {
            log.error("Cannot identify the type of this Announcement");
            return false;
        }
        try {
            AnnounceDO announceDO = new AnnounceDO();
            announceDO.setId(id);
            announceDO.setTitle(node.getTitle());
            announceDO.setContent(node.getContent());
            announceDO.setLink(node.getLink());
            announceDO.setTime(new Date());
            return announceMapper.updateAnnouncement(announceDO, node.getType());
        } catch (Exception e) {
            log.error("updateAnnouncement error=", e);
        }
        return false;
    }

    @Override
    public int countPage(int pageSize) {
        try {
            //Take up the whole
            return (announceMapper.countAnnouncement() + pageSize - 1) / pageSize;
        } catch (Exception e) {
            log.error("Announce All error = ", e);
        }
        return 0;
    }

    @Override
    public List<AnnounceDO> getAnnouncement(int page, int pageSize) {
        try {
//            log.warn();
            return announceMapper.getAnnouncementByPage((page-1) * pageSize, pageSize);
        } catch (Exception e) {
            log.error(", error = ", e);
        }
        return new ArrayList<>();
    }

}
