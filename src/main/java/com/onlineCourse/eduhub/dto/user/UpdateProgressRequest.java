package com.onlineCourse.eduhub.dto.user;

import java.util.List;

public record UpdateProgressRequest(
        List<CourseProgressUpdate> updates
) {}
