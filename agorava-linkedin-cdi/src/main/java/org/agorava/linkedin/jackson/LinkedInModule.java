/*
 * Copyright 2013 Agorava
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.agorava.linkedin.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import org.agorava.linkedin.LinkedIn;
import org.agorava.linkedin.model.CodeAndName;
import org.agorava.linkedin.model.Comment;
import org.agorava.linkedin.model.Comments;
import org.agorava.linkedin.model.Companies;
import org.agorava.linkedin.model.Company;
import org.agorava.linkedin.model.Company.CompanyAddress;
import org.agorava.linkedin.model.Company.CompanyContactInfo;
import org.agorava.linkedin.model.Company.CompanyLocation;
import org.agorava.linkedin.model.CompanyJobUpdate;
import org.agorava.linkedin.model.ConnectionAuthorization;
import org.agorava.linkedin.model.CurrentShare;
import org.agorava.linkedin.model.Education;
import org.agorava.linkedin.model.Group;
import org.agorava.linkedin.model.Group.GroupCount;
import org.agorava.linkedin.model.Group.GroupPosts;
import org.agorava.linkedin.model.Group.GroupRelation;
import org.agorava.linkedin.model.GroupMemberships;
import org.agorava.linkedin.model.GroupSettings;
import org.agorava.linkedin.model.GroupSuggestions;
import org.agorava.linkedin.model.ImAccount;
import org.agorava.linkedin.model.Job;
import org.agorava.linkedin.model.JobBookmark;
import org.agorava.linkedin.model.JobBookmarks;
import org.agorava.linkedin.model.JobPosition;
import org.agorava.linkedin.model.Jobs;
import org.agorava.linkedin.model.Likes;
import org.agorava.linkedin.model.LinkedInConnections;
import org.agorava.linkedin.model.LinkedInDate;
import org.agorava.linkedin.model.LinkedInNetworkUpdate;
import org.agorava.linkedin.model.LinkedInNetworkUpdates;
import org.agorava.linkedin.model.LinkedInProfile;
import org.agorava.linkedin.model.LinkedInProfileFull;
import org.agorava.linkedin.model.LinkedInProfiles;
import org.agorava.linkedin.model.Location;
import org.agorava.linkedin.model.MemberGroup;
import org.agorava.linkedin.model.NetworkStatistics;
import org.agorava.linkedin.model.PersonActivity;
import org.agorava.linkedin.model.PhoneNumber;
import org.agorava.linkedin.model.Position;
import org.agorava.linkedin.model.Post;
import org.agorava.linkedin.model.Post.Attachment;
import org.agorava.linkedin.model.Post.PostRelation;
import org.agorava.linkedin.model.PostComment;
import org.agorava.linkedin.model.PostComments;
import org.agorava.linkedin.model.Product;
import org.agorava.linkedin.model.Product.ProductRecommendation;
import org.agorava.linkedin.model.Products;
import org.agorava.linkedin.model.Recommendation;
import org.agorava.linkedin.model.Relation;
import org.agorava.linkedin.model.Share;
import org.agorava.linkedin.model.TwitterAccount;
import org.agorava.linkedin.model.UpdateAction;
import org.agorava.linkedin.model.UpdateContent;
import org.agorava.linkedin.model.UpdateContentCompany;
import org.agorava.linkedin.model.UpdateContentConnection;
import org.agorava.linkedin.model.UpdateContentFollow;
import org.agorava.linkedin.model.UpdateContentGroup;
import org.agorava.linkedin.model.UpdateContentPersonActivity;
import org.agorava.linkedin.model.UpdateContentRecommendation;
import org.agorava.linkedin.model.UpdateContentShare;
import org.agorava.linkedin.model.UpdateContentStatus;
import org.agorava.linkedin.model.UpdateContentViral;
import org.agorava.linkedin.model.UrlResource;

/**
 * Jackson module for registering mixin annotations against LinkedIn model classes.
 *
 * @author Antoine Sabot-Durand
 */
@LinkedIn
public class LinkedInModule extends SimpleModule {

    public LinkedInModule() {
        super("LinkedInModule");
    }

    @Override
    public void setupModule(SetupContext context) {
        context.setMixInAnnotations(LinkedInConnections.class, LinkedInConnectionsMixin.class);
        context.setMixInAnnotations(LinkedInProfile.class, LinkedInProfileMixin.class);
        context.setMixInAnnotations(LinkedInProfileFull.class, LinkedInProfileFullMixin.class);
        context.setMixInAnnotations(MemberGroup.class, MemberGroupMixin.class);
        context.setMixInAnnotations(Recommendation.class, RecommendationMixin.class);
        context.setMixInAnnotations(PersonActivity.class, PersonActivityMixin.class);
        context.setMixInAnnotations(Job.class, JobMixin.class);
        context.setMixInAnnotations(JobPosition.class, JobPositionMixin.class);
        context.setMixInAnnotations(JobBookmark.class, JobBookmarkMixin.class);
        context.setMixInAnnotations(JobBookmarks.class, JobBookmarksMixin.class);
        context.setMixInAnnotations(Company.class, CompanyMixin.class);
        context.setMixInAnnotations(CompanyLocation.class, CompanyLocationMixin.class);
        context.setMixInAnnotations(CompanyAddress.class, CompanyAddressMixin.class);
        context.setMixInAnnotations(CompanyContactInfo.class, CompanyContactInfoMixin.class);
        context.setMixInAnnotations(CompanyJobUpdate.class, CompanyJobUpdateMixin.class);
        context.setMixInAnnotations(CodeAndName.class, CodeAndNameMixin.class);
        context.setMixInAnnotations(UpdateAction.class, UpdateActionMixin.class);
        context.setMixInAnnotations(CurrentShare.class, CurrentShareMixin.class);
        context.setMixInAnnotations(Share.class, ShareMixin.class);
        context.setMixInAnnotations(Share.ShareContent.class, ShareContentMixin.class);
        context.setMixInAnnotations(Share.ShareSource.class, ShareSourceMixin.class);
        context.setMixInAnnotations(Comment.class, CommentMixin.class);
        context.setMixInAnnotations(Comments.class, CommentsMixin.class);
        context.setMixInAnnotations(Likes.class, LikesMixin.class);
        context.setMixInAnnotations(Position.class, PositionMixin.class);
        context.setMixInAnnotations(ImAccount.class, ImAccountMixin.class);
        context.setMixInAnnotations(TwitterAccount.class, TwitterAccountMixin.class);
        context.setMixInAnnotations(UrlResource.class, UrlResourceMixin.class);
        context.setMixInAnnotations(PhoneNumber.class, PhoneNumberMixin.class);
        context.setMixInAnnotations(Education.class, EducationMixin.class);
        context.setMixInAnnotations(Location.class, LocationMixin.class);
        context.setMixInAnnotations(LinkedInDate.class, LinkedInDateMixin.class);
        context.setMixInAnnotations(Relation.class, RelationMixin.class);
        context.setMixInAnnotations(NetworkStatistics.class, NetworkStatisticsMixin.class);
        context.setMixInAnnotations(Companies.class, CompaniesMixin.class);
        context.setMixInAnnotations(LinkedInProfiles.class, LinkedInProfilesMixin.class);
        context.setMixInAnnotations(Jobs.class, JobsMixin.class);
        context.setMixInAnnotations(Product.class, ProductMixin.class);
        context.setMixInAnnotations(ProductRecommendation.class, ProductRecommendationMixin.class);
        context.setMixInAnnotations(Products.class, ProductsMixin.class);
        context.setMixInAnnotations(ConnectionAuthorization.class, ConnectionAuthorizationMixin.class);
        context.setMixInAnnotations(LinkedInNetworkUpdate.class, LinkedInNetworkUpdateMixin.class);
        context.setMixInAnnotations(LinkedInNetworkUpdates.class, LinkedInNetworkUpdatesMixin.class);
        context.setMixInAnnotations(UpdateContent.class, UpdateContentMixin.class);
        context.setMixInAnnotations(UpdateContentConnection.class, UpdateContentConnectionMixin.class);
        context.setMixInAnnotations(UpdateContentStatus.class, UpdateContentStatusMixin.class);
        context.setMixInAnnotations(UpdateContentGroup.class, UpdateContentGroupMixin.class);
        context.setMixInAnnotations(UpdateContentRecommendation.class, UpdateContentRecommendationMixin.class);
        context.setMixInAnnotations(UpdateContentPersonActivity.class, UpdateContentPersonActivityMixin.class);
        context.setMixInAnnotations(UpdateContentFollow.class, UpdateContentFollowMixin.class);
        context.setMixInAnnotations(UpdateContentViral.class, UpdateContentViralMixin.class);
        context.setMixInAnnotations(UpdateContentShare.class, UpdateContentShareMixin.class);
        context.setMixInAnnotations(UpdateContentCompany.class, UpdateContentCompanyMixin.class);
        context.setMixInAnnotations(Group.class, GroupMixin.class);
        context.setMixInAnnotations(GroupCount.class, GroupCountMixin.class);
        context.setMixInAnnotations(GroupPosts.class, GroupPostsMixin.class);
        context.setMixInAnnotations(GroupRelation.class, GroupRelationMixin.class);
        context.setMixInAnnotations(Post.class, PostMixin.class);
        context.setMixInAnnotations(PostRelation.class, PostRelationMixin.class);
        context.setMixInAnnotations(Attachment.class, AttachmentMixin.class);
        context.setMixInAnnotations(PostComments.class, PostCommentsMixin.class);
        context.setMixInAnnotations(PostComment.class, PostCommentMixin.class);
        context.setMixInAnnotations(GroupSuggestions.class, GroupSuggestionsMixin.class);
        context.setMixInAnnotations(GroupMemberships.class, GroupMembershipsMixin.class);
        context.setMixInAnnotations(GroupSettings.class, GroupSettingsMixin.class);
    }

}
