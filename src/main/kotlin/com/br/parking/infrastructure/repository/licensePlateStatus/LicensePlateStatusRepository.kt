package com.br.parking.infrastructure.repository.licensePlateStatus

import com.br.parking.shared.error.LicensePlateStatusGenericErrorException
import com.br.parking.shared.error.LicensePlateStatusNotFoundErrorException
import org.slf4j.LoggerFactory
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.sql.ResultSet

@Repository
class LicensePlateStatusRepository(
  private val jdbcTemplate: JdbcTemplate,
  private val queries: LicensePlateStatusQuery
) {

  private val log = LoggerFactory.getLogger(LicensePlateStatusRepository::class.java)

  fun findLicensePlateStatus(licensePlate: String): LicensePlateStatusOutput? {
    try {
        log.info("Buscando dados da placa: {}", licensePlate)

        val sql = queries.select()
        return jdbcTemplate.query(sql, rowMapper(), licensePlate).firstOrNull()
    } catch (e: EmptyResultDataAccessException) {
      throw LicensePlateStatusNotFoundErrorException()
    } catch (e: Exception) {
      throw LicensePlateStatusGenericErrorException(e)
    }

  }

  private fun rowMapper() = RowMapper { rs: ResultSet, _: Int ->
    LicensePlateStatusOutput(
      licensePlate = rs.getString("licensePlate"),
      parklotStatus = rs.getString("parklotStatus"),
      lat = rs.getDouble("lat"),
      lng = rs.getDouble("lng"),
      entryTime = rs.getTimestamp("entryTime")?.toLocalDateTime(),
      timeParked = rs.getTimestamp("timeParked")?.toLocalDateTime(),
      charge = rs.getBigDecimal("charge"),
      basePrice = rs.getBigDecimal("basePrice"),
      duration = rs.getInt("duration")
    )
  }
}
