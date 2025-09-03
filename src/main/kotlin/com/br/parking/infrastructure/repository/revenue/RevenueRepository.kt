package com.br.parking.infrastructure.repository.revenue

import com.br.parking.shared.error.RevenueGenericErrorException
import com.br.parking.shared.error.RevenueNotFoundErrorException
import org.slf4j.LoggerFactory
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import java.sql.ResultSet
import java.time.LocalDate

@Repository
class RevenueRepository(
  private val jdbcTemplate: JdbcTemplate,
  private val queries: RevenueQuery
) {
  private val log = LoggerFactory.getLogger(RevenueRepository::class.java)
  fun findByDateAndSector(sectorCode: String, monthDate: LocalDate): List<RevenueOutput> {
     try{
         log.info("Buscando faturamento do setor: {}, na data: monthDate: {}",sectorCode, monthDate)

         val sql = queries.select()
         return jdbcTemplate.query(sql, revenueRowMapper(), sectorCode, monthDate)
      } catch (e: EmptyResultDataAccessException) {
      throw RevenueNotFoundErrorException()
    } catch (e: Exception) {
      throw RevenueGenericErrorException(e)
    }
  }

  private fun revenueRowMapper() = RowMapper { rs: ResultSet, _: Int ->
    RevenueOutput(
      charge = rs.getBigDecimal("charge"),
      entryTime = rs.getTimestamp("entryTime").toLocalDateTime(),
      exitTime = rs.getTimestamp("exitTime")?.toLocalDateTime(),
      finalPrice = rs.getBigDecimal("finalPrice")?: BigDecimal.ZERO
    )
  }
}