package org.dmcs.transaction.analytics.test.framework.utils.retries

import java.time.LocalDateTime

case class FailedRun(startTime: LocalDateTime, endTime: LocalDateTime, cause: Throwable)
