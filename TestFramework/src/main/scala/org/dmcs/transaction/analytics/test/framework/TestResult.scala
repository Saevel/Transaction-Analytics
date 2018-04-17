package org.dmcs.transaction.analytics.test.framework

import java.time.LocalDateTime

case class TestResult[StatisticType](testName: String,
                                     phaseId: Long,
                                     expected: StatisticType,
                                     actual: StatisticType,
                                     passed: Boolean,
                                     startTimestamp: LocalDateTime,
                                     endTimestamp: LocalDateTime)