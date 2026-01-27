package com.onlineCourse.eduhub.service;

import com.onlineCourse.eduhub.dto.user.UpdateProfileRequest;

public interface ProfileService {
    void updateCurrentUserProfile(UpdateProfileRequest request);
}
