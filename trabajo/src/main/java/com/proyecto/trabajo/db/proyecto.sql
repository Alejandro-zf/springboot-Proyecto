-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3308
-- Tiempo de generación: 08-12-2025 a las 20:25:45
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `proyecto`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categoria`
--

CREATE TABLE `categoria` (
  `id` tinyint(4) NOT NULL,
  `estado` tinyint(4) NOT NULL,
  `nom_categoria` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `elementos`
--

CREATE TABLE `elementos` (
  `id` bigint(20) NOT NULL,
  `componentes` varchar(255) DEFAULT NULL,
  `estadosoelement` tinyint(4) NOT NULL,
  `marca` varchar(50) DEFAULT NULL,
  `nom_elemento` varchar(100) NOT NULL,
  `num_serie` varchar(50) DEFAULT NULL,
  `obser` varchar(150) NOT NULL,
  `sub_categoria` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `elemento_solicitudes`
--

CREATE TABLE `elemento_solicitudes` (
  `elemento_id` bigint(20) NOT NULL,
  `solicitud_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `espacio`
--

CREATE TABLE `espacio` (
  `id` int(11) NOT NULL,
  `descripcion` varchar(900) NOT NULL,
  `estadoespacio` tinyint(4) NOT NULL,
  `imagenes` longtext DEFAULT NULL,
  `nom_espa` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estado_solicitudes`
--

CREATE TABLE `estado_solicitudes` (
  `id` int(11) NOT NULL,
  `nom_esta` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `estado_solicitudes`
--

INSERT INTO `estado_solicitudes` (`id`, `nom_esta`) VALUES
(1, 'Pendiente'),
(2, 'Aprobado'),
(3, 'Rechazado'),
(4, 'Cancelado'),
(5, 'Finalizado');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estado_ticket`
--

CREATE TABLE `estado_ticket` (
  `id_estado` tinyint(4) NOT NULL,
  `nom_estado` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `estado_ticket`
--

INSERT INTO `estado_ticket` (`id_estado`, `nom_estado`) VALUES
(1, 'Activo'),
(2, 'Pendiente'),
(3, 'Terminado'),
(4, 'Inactivo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `prestamos`
--

CREATE TABLE `prestamos` (
  `id` bigint(20) NOT NULL,
  `estado` tinyint(4) NOT NULL,
  `fecha_entre` datetime(6) DEFAULT NULL,
  `fecha_recep` datetime(6) DEFAULT NULL,
  `tipo_prest` varchar(30) NOT NULL,
  `id_espacio` int(11) DEFAULT NULL,
  `id_solicitud` bigint(20) NOT NULL,
  `id_user` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `prestamos_elementos`
--

CREATE TABLE `prestamos_elementos` (
  `cantidad` int(11) DEFAULT NULL,
  `obser_prest` varchar(255) NOT NULL,
  `elemento_id` bigint(20) NOT NULL,
  `prestamos_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `problemas`
--

CREATE TABLE `problemas` (
  `id` tinyint(4) NOT NULL,
  `desc_problema` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `problemas`
--

INSERT INTO `problemas` (`id`, `desc_problema`) VALUES
(1, 'Problemas con el Office'),
(2, 'Problemas con credenciales'),
(3, 'Sobrecalentamiento'),
(4, 'Se apaga solo'),
(5, 'Demasiado tiempo cargando'),
(6, 'No enciende'),
(7, 'Sin internet'),
(8, 'Puertos dañados'),
(9, 'Bloqueado');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `roles`
--

CREATE TABLE `roles` (
  `id` bigint(20) NOT NULL,
  `nom_rol` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `roles`
--

INSERT INTO `roles` (`id`, `nom_rol`) VALUES
(1, 'Instructor'),
(2, 'Administrador'),
(3, 'Tecnico');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `roles_usuario`
--

CREATE TABLE `roles_usuario` (
  `roles_id` bigint(20) NOT NULL,
  `usuario_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `roles_usuario`
--

INSERT INTO `roles_usuario` (`roles_id`, `usuario_id`) VALUES
(1, 1),
(2, 2),
(3, 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `solicitudes`
--

CREATE TABLE `solicitudes` (
  `id` bigint(20) NOT NULL,
  `ambiente` varchar(35) NOT NULL,
  `cantidad` int(11) DEFAULT NULL,
  `fecha_fin` datetime(6) DEFAULT NULL,
  `fecha_inicio` datetime(6) DEFAULT NULL,
  `mensaje` varchar(255) DEFAULT NULL,
  `num_ficha` int(11) DEFAULT NULL,
  `id_categoria` tinyint(4) DEFAULT NULL,
  `id_espacio` int(11) DEFAULT NULL,
  `id_estado_solicitud` int(11) DEFAULT NULL,
  `id_subcategoria` bigint(20) DEFAULT NULL,
  `id_usuari` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sub_categoria`
--

CREATE TABLE `sub_categoria` (
  `id` bigint(20) NOT NULL,
  `estado` tinyint(4) NOT NULL,
  `nom_subcategoria` varchar(50) NOT NULL,
  `categoria` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tickets`
--

CREATE TABLE `tickets` (
  `id` bigint(20) NOT NULL,
  `ambiente` varchar(30) NOT NULL,
  `estado` tinyint(4) NOT NULL,
  `fecha_finn` datetime(6) DEFAULT NULL,
  `fecha_ini` datetime(6) DEFAULT NULL,
  `imageness` longtext DEFAULT NULL,
  `observaciones` varchar(255) DEFAULT NULL,
  `elementos` bigint(20) NOT NULL,
  `id_est_tick` tinyint(4) NOT NULL,
  `problemas` tinyint(4) NOT NULL,
  `id_usu` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tip_documento`
--

CREATE TABLE `tip_documento` (
  `id` tinyint(4) NOT NULL,
  `tipo_doc` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tip_documento`
--

INSERT INTO `tip_documento` (`id`, `tipo_doc`) VALUES
(1, 'Cédula de Ciudadanía'),
(2, 'Cédula de Extranjería'),
(3, 'Pasaporte');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `trasabilidad`
--

CREATE TABLE `trasabilidad` (
  `id` bigint(20) NOT NULL,
  `fecha` date DEFAULT NULL,
  `observacion` varchar(255) DEFAULT NULL,
  `id_elemento` bigint(20) NOT NULL,
  `id_ticket` bigint(20) NOT NULL,
  `id_usuario` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` bigint(20) NOT NULL,
  `ape_usu` varchar(50) NOT NULL,
  `correo` varchar(100) NOT NULL,
  `estado` tinyint(4) NOT NULL,
  `nom_usu` varchar(50) NOT NULL,
  `num_doc` bigint(20) DEFAULT NULL,
  `password` varchar(150) NOT NULL,
  `tip_document` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `ape_usu`, `correo`, `estado`, `nom_usu`, `num_doc`, `password`, `tip_document`) VALUES
(1, 'Sistema', 'instructor@tech.com', 1, 'Instructor', 1234567890, '$2a$10$OjpYeU.uEm.PezFtvWDwnegAqo6OueszPfXY5oPqyWNgCRtUNNkxa', 1),
(2, 'Sistema', 'admin@tech.com', 1, 'Administrador', 9876543210, '$2a$10$64BY3VwRzwX.RHOHpo9kAeFx.5DCVjLpVau7OpEuWsqVrIuuvUbL.', 1),
(3, 'Sistema', 'tecnico@tech.com', 1, 'Tecnico', 5555555555, '$2a$10$Q9M9Shc.MYCEu7Fkl71z2uoZbOZvRXCNoc7SJWTJ6JC5SfwmcFFyS', 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `categoria`
--
ALTER TABLE `categoria`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `elementos`
--
ALTER TABLE `elementos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_Id_subcategoria` (`sub_categoria`);

--
-- Indices de la tabla `elemento_solicitudes`
--
ALTER TABLE `elemento_solicitudes`
  ADD PRIMARY KEY (`elemento_id`,`solicitud_id`),
  ADD KEY `FK_elemento_solicitudes_solicitudes` (`solicitud_id`);

--
-- Indices de la tabla `espacio`
--
ALTER TABLE `espacio`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `estado_solicitudes`
--
ALTER TABLE `estado_solicitudes`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `estado_ticket`
--
ALTER TABLE `estado_ticket`
  ADD PRIMARY KEY (`id_estado`);

--
-- Indices de la tabla `prestamos`
--
ALTER TABLE `prestamos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_Id_espaciio` (`id_espacio`),
  ADD KEY `FK_Id_solicitud` (`id_solicitud`),
  ADD KEY `FK_Id_user` (`id_user`);

--
-- Indices de la tabla `prestamos_elementos`
--
ALTER TABLE `prestamos_elementos`
  ADD PRIMARY KEY (`elemento_id`,`prestamos_id`),
  ADD KEY `FK_prestamos_elementos_prestamos` (`prestamos_id`);

--
-- Indices de la tabla `problemas`
--
ALTER TABLE `problemas`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `roles_usuario`
--
ALTER TABLE `roles_usuario`
  ADD PRIMARY KEY (`roles_id`,`usuario_id`),
  ADD KEY `FK_usuario_roles_usuario` (`usuario_id`);

--
-- Indices de la tabla `solicitudes`
--
ALTER TABLE `solicitudes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_Id_categoria` (`id_categoria`),
  ADD KEY `FK_Id_espacio` (`id_espacio`),
  ADD KEY `FK_Id_estado_solicitud` (`id_estado_solicitud`),
  ADD KEY `FK_Solicitud_Subcategoria` (`id_subcategoria`),
  ADD KEY `FK_Id_usuari` (`id_usuari`);

--
-- Indices de la tabla `sub_categoria`
--
ALTER TABLE `sub_categoria`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_SubCat_Categoria` (`categoria`);

--
-- Indices de la tabla `tickets`
--
ALTER TABLE `tickets`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_Id_elementos` (`elementos`),
  ADD KEY `FK_Id_estado` (`id_est_tick`),
  ADD KEY `Fk_Id_Problemas` (`problemas`),
  ADD KEY `FK_Id_usu` (`id_usu`);

--
-- Indices de la tabla `tip_documento`
--
ALTER TABLE `tip_documento`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `trasabilidad`
--
ALTER TABLE `trasabilidad`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_id_elemento` (`id_elemento`),
  ADD KEY `FK_id_ticket` (`id_ticket`),
  ADD KEY `FK_id_usuario` (`id_usuario`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_Tip_document` (`tip_document`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `categoria`
--
ALTER TABLE `categoria`
  MODIFY `id` tinyint(4) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `elementos`
--
ALTER TABLE `elementos`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `espacio`
--
ALTER TABLE `espacio`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `estado_solicitudes`
--
ALTER TABLE `estado_solicitudes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `estado_ticket`
--
ALTER TABLE `estado_ticket`
  MODIFY `id_estado` tinyint(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `prestamos`
--
ALTER TABLE `prestamos`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `problemas`
--
ALTER TABLE `problemas`
  MODIFY `id` tinyint(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `roles`
--
ALTER TABLE `roles`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `solicitudes`
--
ALTER TABLE `solicitudes`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `sub_categoria`
--
ALTER TABLE `sub_categoria`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `tickets`
--
ALTER TABLE `tickets`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `tip_documento`
--
ALTER TABLE `tip_documento`
  MODIFY `id` tinyint(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `trasabilidad`
--
ALTER TABLE `trasabilidad`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `elementos`
--
ALTER TABLE `elementos`
  ADD CONSTRAINT `FK_Id_subcategoria` FOREIGN KEY (`sub_categoria`) REFERENCES `sub_categoria` (`id`);

--
-- Filtros para la tabla `elemento_solicitudes`
--
ALTER TABLE `elemento_solicitudes`
  ADD CONSTRAINT `FK_elemento_solicitudes_elementos` FOREIGN KEY (`elemento_id`) REFERENCES `elementos` (`id`),
  ADD CONSTRAINT `FK_elemento_solicitudes_solicitudes` FOREIGN KEY (`solicitud_id`) REFERENCES `solicitudes` (`id`);

--
-- Filtros para la tabla `prestamos`
--
ALTER TABLE `prestamos`
  ADD CONSTRAINT `FK_Id_espaciio` FOREIGN KEY (`id_espacio`) REFERENCES `espacio` (`id`),
  ADD CONSTRAINT `FK_Id_solicitud` FOREIGN KEY (`id_solicitud`) REFERENCES `solicitudes` (`id`),
  ADD CONSTRAINT `FK_Id_user` FOREIGN KEY (`id_user`) REFERENCES `usuarios` (`id`);

--
-- Filtros para la tabla `prestamos_elementos`
--
ALTER TABLE `prestamos_elementos`
  ADD CONSTRAINT `FK_prestamos_elementos_elementos` FOREIGN KEY (`elemento_id`) REFERENCES `elementos` (`id`),
  ADD CONSTRAINT `FK_prestamos_elementos_prestamos` FOREIGN KEY (`prestamos_id`) REFERENCES `prestamos` (`id`);

--
-- Filtros para la tabla `roles_usuario`
--
ALTER TABLE `roles_usuario`
  ADD CONSTRAINT `FK_roles_usuario_roles` FOREIGN KEY (`roles_id`) REFERENCES `roles` (`id`),
  ADD CONSTRAINT `FK_usuario_roles_usuario` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`);

--
-- Filtros para la tabla `solicitudes`
--
ALTER TABLE `solicitudes`
  ADD CONSTRAINT `FK_Id_categoria` FOREIGN KEY (`id_categoria`) REFERENCES `categoria` (`id`),
  ADD CONSTRAINT `FK_Id_espacio` FOREIGN KEY (`id_espacio`) REFERENCES `espacio` (`id`),
  ADD CONSTRAINT `FK_Id_estado_solicitud` FOREIGN KEY (`id_estado_solicitud`) REFERENCES `estado_solicitudes` (`id`),
  ADD CONSTRAINT `FK_Id_usuari` FOREIGN KEY (`id_usuari`) REFERENCES `usuarios` (`id`),
  ADD CONSTRAINT `FK_Solicitud_Subcategoria` FOREIGN KEY (`id_subcategoria`) REFERENCES `sub_categoria` (`id`);

--
-- Filtros para la tabla `sub_categoria`
--
ALTER TABLE `sub_categoria`
  ADD CONSTRAINT `FK_SubCat_Categoria` FOREIGN KEY (`categoria`) REFERENCES `categoria` (`id`);

--
-- Filtros para la tabla `tickets`
--
ALTER TABLE `tickets`
  ADD CONSTRAINT `FK_Id_elementos` FOREIGN KEY (`elementos`) REFERENCES `elementos` (`id`),
  ADD CONSTRAINT `FK_Id_estado` FOREIGN KEY (`id_est_tick`) REFERENCES `estado_ticket` (`id_estado`),
  ADD CONSTRAINT `FK_Id_usu` FOREIGN KEY (`id_usu`) REFERENCES `usuarios` (`id`),
  ADD CONSTRAINT `Fk_Id_Problemas` FOREIGN KEY (`problemas`) REFERENCES `problemas` (`id`);

--
-- Filtros para la tabla `trasabilidad`
--
ALTER TABLE `trasabilidad`
  ADD CONSTRAINT `FK_id_elemento` FOREIGN KEY (`id_elemento`) REFERENCES `elementos` (`id`),
  ADD CONSTRAINT `FK_id_ticket` FOREIGN KEY (`id_ticket`) REFERENCES `tickets` (`id`),
  ADD CONSTRAINT `FK_id_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`);

--
-- Filtros para la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD CONSTRAINT `FK_Tip_document` FOREIGN KEY (`tip_document`) REFERENCES `tip_documento` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
