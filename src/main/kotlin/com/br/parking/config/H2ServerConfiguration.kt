package com.br.parking.config

import org.h2.tools.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.sql.SQLException

@Configuration
class H2ServerConfiguration {

  @Bean(initMethod = "start", destroyMethod = "stop")
  @Throws(SQLException::class)
  fun h2TcpServer(): Server {
    return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092")
  }
}
