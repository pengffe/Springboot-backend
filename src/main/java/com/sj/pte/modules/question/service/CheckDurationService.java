package com.sj.pte.modules.question.service;/**
 * Created by TUTEHUB on 2021/6/4 12:48 PM.
 * Copyright © 2021 TUTEHUB. All rights reserved.
 * ------------------------
 * Non-disclosure Terms
 * -------------------------
 * These codes are TUTEHUB's property, as the developer, such as employee, contractor, and intern, working for TUTEHUB cannot disclose, spread, copy, preserve, sell, and do other activities without the consent of TUTEHUB.
 * Developer dph agrees with above terms.
 * Technique Support: jobyme88.com
 * Contact: noreply@fengcaoculture.com
 */


import com.sj.pte.modules.question.bean.MNQuestion;

import java.util.List;

/**
 * @description
 */
public interface CheckDurationService {

    <T extends MNQuestion> List<T> checkDuration(List<T> tClass);
}
