package com.b304.bobs.api.service.CommunityComment;

import com.b304.bobs.api.request.CommunityComment.CommunityCommentModiReq;
import com.b304.bobs.api.request.CommunityComment.CommunityCommentReq;
import com.b304.bobs.api.response.CommunityComment.CommunityCommentRes;
import com.b304.bobs.api.response.ModifyRes;
import com.b304.bobs.api.response.PageRes;
import com.b304.bobs.db.entity.CommunityComment;
import com.b304.bobs.db.repository.CommunityCommentRepository;
import com.b304.bobs.db.repository.CommunityRepository;
import com.b304.bobs.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CommunityCommentServiceImpl implements CommunityCommentService{

    final private CommunityCommentRepository communityCommentRepository;
    final private UserRepository userRepository;
    final private CommunityRepository communityRepository;

    @Override
    public CommunityCommentRes createComment(CommunityCommentReq communityCommentReq) throws Exception {
        CommunityComment communityComment = new CommunityComment();
        CommunityCommentRes result = new CommunityCommentRes();

        try  {
            communityComment.setCommunity_comment_content(communityCommentReq.getCommunity_comment_content());
            communityComment.setCommunity_comment_created(LocalDateTime.now());

            if(userRepository.findById(communityCommentReq.getUser_id()).isPresent()){
                communityComment.setUser(userRepository.findById(communityCommentReq.getUser_id()).orElse(null));

                if(communityRepository.findById(communityCommentReq.getCommunity_id()).isPresent()){
                    communityComment.setCommunity(communityRepository.findById(communityCommentReq.getCommunity_id()).orElse(null));
                    result = new CommunityCommentRes(communityCommentRepository.save(communityComment));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ModifyRes deleteComment(Long community_comment_id) throws Exception {
        ModifyRes modifyRes = new ModifyRes();

        try {
            int result = communityCommentRepository.deleteComment(community_comment_id);
            modifyRes.setResult(result);
            modifyRes.setId(community_comment_id);
            return modifyRes;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return modifyRes;
    }

    @Override
    public CommunityCommentRes modifyComment(CommunityCommentModiReq communityCommentModiReq) throws Exception {
        CommunityCommentRes tmp = new CommunityCommentRes();
        try {
            CommunityComment communityComment = communityCommentRepository.findOneById(communityCommentModiReq.getCommunity_comment_id());

            if(communityComment.equals(new CommunityComment())) return tmp;
            int result = communityCommentRepository.modifyComment(
                    communityCommentModiReq.getCommunity_comment_content(),
                    communityCommentModiReq.getCommunity_comment_id()
            );
            if(result ==1) return new CommunityCommentRes(communityComment, communityCommentModiReq);
            return tmp;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tmp;
    }


    @Override
    public PageRes findAll(Long comment_id) throws Exception {
        PageRes pageRes = new PageRes();

        try {
            List<CommunityComment> comments = communityCommentRepository.findAll(comment_id);
            if(comments.isEmpty()) return pageRes;

            pageRes
                    .setContents(comments.stream()
                            .map(CommunityCommentRes::new)
                            .collect(Collectors.toList())
                    );
        }catch (Exception e){
            e.printStackTrace();
        }
        return pageRes;
    }

    @Override
    public CommunityCommentRes findById(Long comment_id) throws Exception{
        CommunityComment communityComment;
        CommunityCommentRes communityCommentRes = new CommunityCommentRes();

        try {
            communityComment = communityCommentRepository.findOneById(comment_id);
            communityCommentRes = new CommunityCommentRes(communityComment);

            return communityCommentRes;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return communityCommentRes;
    }

}