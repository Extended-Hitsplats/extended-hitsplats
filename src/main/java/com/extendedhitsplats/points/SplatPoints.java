/*
 * Copyright (c) 2022, Ferrariic <ferrariictweet@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.extendedhitsplats.points;

import net.runelite.api.Point;

import java.util.*;

public class SplatPoints {
    public static Map<Integer, Point> splatPoints = new HashMap<Integer, Point>() {{
        put(0, new Point(0,0));
        put(1, new Point(0,-20));
        put(2, new Point(20,-10));
        put(3, new Point(-20,-10));
        put(4, new Point(20,-30));
        put(5, new Point(-40,-20));
        put(6, new Point(-20,-30));
        put(7, new Point(40,-20));
        put(8, new Point(-40,0));
        put(9, new Point(20,10));
        put(10, new Point(40,0));
        put(11, new Point(-20,10));
        put(12, new Point(-40,-40));
        put(13, new Point(60,-30));
        put(14, new Point(40,-40));
        put(15, new Point(60,-10));
        put(16, new Point(0,-40));
        put(17, new Point(60,10));
        put(18, new Point(-60,-30));
        put(19, new Point(-60,-10));
        put(20, new Point(-40,20));
        put(21, new Point(40,20));
        put(22, new Point(-60,10));
        put(23, new Point(0,20));
        put(24, new Point(-60,-50));
        put(25, new Point(-20,-50));
        put(26, new Point(-80,-40));
        put(27, new Point(-80,-20));
        put(28, new Point(80,-20));
        put(29, new Point(80,0));
        put(30, new Point(-80,0));
        put(31, new Point(60,30));
        put(32, new Point(60,-50));
        put(33, new Point(-80,20));
        put(34, new Point(80,20));
        put(35, new Point(-20,30));
        put(36, new Point(20,30));
        put(37, new Point(20,-50));
        put(38, new Point(80,-40));
        put(39, new Point(-60,30));
        put(40, new Point(-100,10));
        put(41, new Point(-80,40));
        put(42, new Point(-80,-60));
        put(43, new Point(-40,40));
        put(44, new Point(100,-50));
        put(45, new Point(-100,-50));
        put(46, new Point(0,40));
        put(47, new Point(100,-10));
        put(48, new Point(-40,-60));
        put(49, new Point(-100,-10));
        put(50, new Point(0,-60));
        put(51, new Point(100,30));
        put(52, new Point(40,40));
        put(53, new Point(-100,30));
        put(54, new Point(80,40));
        put(55, new Point(40,-60));
        put(56, new Point(100,-30));
        put(57, new Point(-100,-30));
        put(58, new Point(100,10));
        put(59, new Point(80,-60));
        put(60, new Point(100,50));
        put(61, new Point(120,-60));
        put(62, new Point(-100,50));
        put(63, new Point(-120,-60));
        put(64, new Point(120,-20));
        put(65, new Point(-20,-70));
        put(66, new Point(-120,-20));
        put(67, new Point(120,20));
        put(68, new Point(-60,50));
        put(69, new Point(-120,20));
        put(70, new Point(20,-70));
        put(71, new Point(-20,50));
        put(72, new Point(120,-40));
        put(73, new Point(60,-70));
        put(74, new Point(120,0));
        put(75, new Point(-120,-40));
        put(76, new Point(100,-70));
        put(77, new Point(20,50));
        put(78, new Point(-100,-70));
        put(79, new Point(120,40));
        put(80, new Point(-120,0));
        put(81, new Point(60,50));
        put(82, new Point(-120,40));
        put(83, new Point(-60,-70));
        put(84, new Point(-140,50));
        put(85, new Point(140,-30));
        put(86, new Point(40,-80));
        put(87, new Point(140,10));
        put(88, new Point(-140,-50));
        put(89, new Point(140,50));
        put(90, new Point(80,60));
        put(91, new Point(-140,-10));
        put(92, new Point(80,-80));
        put(93, new Point(120,60));
        put(94, new Point(120,-80));
        put(95, new Point(-140,30));
        put(96, new Point(140,-50));
        put(97, new Point(-120,60));
        put(98, new Point(-80,60));
        put(99, new Point(140,-10));
        put(100, new Point(-120,-80));
        put(101, new Point(-80,-80));
        put(102, new Point(-140,-70));
        put(103, new Point(140,30));
        put(104, new Point(-40,60));
        put(105, new Point(40,60));
        put(106, new Point(-140,-30));
        put(107, new Point(0,60));
        put(108, new Point(-40,-80));
        put(109, new Point(-140,10));
        put(110, new Point(140,-70));
        put(111, new Point(0,-80));
        put(112, new Point(160,40));
        put(113, new Point(-160,-20));
        put(114, new Point(-20,70));
        put(115, new Point(60,-90));
        put(116, new Point(-140,-90));
        put(117, new Point(100,-90));
        put(118, new Point(-160,20));
        put(119, new Point(-100,-90));
        put(120, new Point(160,-60));
        put(121, new Point(-160,60));
        put(122, new Point(20,70));
        put(123, new Point(140,-90));
        put(124, new Point(160,-20));
        put(125, new Point(-160,-80));
        put(126, new Point(-60,-90));
        put(127, new Point(160,20));
        put(128, new Point(-160,-40));
        put(129, new Point(60,70));
        put(130, new Point(-160,0));
        put(131, new Point(-140,70));
        put(132, new Point(160,60));
        put(133, new Point(100,70));
        put(134, new Point(-100,70));
        put(135, new Point(-20,-90));
        put(136, new Point(-160,40));
        put(137, new Point(160,-80));
        put(138, new Point(140,70));
        put(139, new Point(160,-40));
        put(140, new Point(-60,70));
        put(141, new Point(20,-90));
        put(142, new Point(160,0));
        put(143, new Point(-160,-60));
        put(144, new Point(180,-70));
        put(145, new Point(-180,-50));
        put(146, new Point(-80,80));
        put(147, new Point(-80,-100));
        put(148, new Point(180,-30));
        put(149, new Point(160,-100));
        put(150, new Point(160,80));
        put(151, new Point(-180,-10));
        put(152, new Point(-120,80));
        put(153, new Point(180,10));
        put(154, new Point(-180,30));
        put(155, new Point(-40,-100));
        put(156, new Point(-40,80));
        put(157, new Point(180,50));
        put(158, new Point(-180,70));
        put(159, new Point(0,-100));
        put(160, new Point(180,-90));
        put(161, new Point(0,80));
        put(162, new Point(-180,-70));
        put(163, new Point(180,-50));
        put(164, new Point(-180,-30));
        put(165, new Point(40,80));
        put(166, new Point(40,-100));
        put(167, new Point(180,-10));
        put(168, new Point(-180,10));
        put(169, new Point(180,30));
        put(170, new Point(-180,50));
        put(171, new Point(-160,-100));
        put(172, new Point(-120,-100));
        put(173, new Point(-160,80));
        put(174, new Point(80,80));
        put(175, new Point(80,-100));
        put(176, new Point(180,70));
        put(177, new Point(-180,-90));
        put(178, new Point(120,-100));
        put(179, new Point(120,80));
        put(180, new Point(200,-40));
        put(181, new Point(60,90));
        put(182, new Point(-20,-110));
        put(183, new Point(-140,90));
        put(184, new Point(-200,-40));
        put(185, new Point(100,90));
        put(186, new Point(200,0));
        put(187, new Point(-100,90));
        put(188, new Point(-200,0));
        put(189, new Point(200,40));
        put(190, new Point(20,-110));
        put(191, new Point(200,80));
        put(192, new Point(200,-100));
        put(193, new Point(-200,40));
        put(194, new Point(140,90));
        put(195, new Point(-180,-110));
        put(196, new Point(180,90));
        put(197, new Point(200,-60));
        put(198, new Point(-60,90));
        put(199, new Point(-200,-100));
        put(200, new Point(-200,80));
        put(201, new Point(60,-110));
        put(202, new Point(200,-20));
        put(203, new Point(-140,-110));
        put(204, new Point(-200,-60));
        put(205, new Point(100,-110));
        put(206, new Point(-100,-110));
        put(207, new Point(-20,90));
        put(208, new Point(200,20));
        put(209, new Point(-200,-20));
        put(210, new Point(-200,20));
        put(211, new Point(200,60));
        put(212, new Point(140,-110));
        put(213, new Point(20,90));
        put(214, new Point(-60,-110));
        put(215, new Point(-200,60));
        put(216, new Point(180,-110));
        put(217, new Point(-180,90));
        put(218, new Point(200,-80));
        put(219, new Point(-200,-80));
        put(220, new Point(40,-120));
        put(221, new Point(-220,-10));
        put(222, new Point(220,70));
        put(223, new Point(220,-110));
        put(224, new Point(40,100));
        put(225, new Point(-220,30));
        put(226, new Point(220,-70));
        put(227, new Point(-160,-120));
        put(228, new Point(-220,70));
        put(229, new Point(-220,-110));
        put(230, new Point(80,-120));
        put(231, new Point(220,-30));
        put(232, new Point(80,100));
        put(233, new Point(120,-120));
        put(234, new Point(-160,100));
        put(235, new Point(-220,-70));
        put(236, new Point(120,100));
        put(237, new Point(220,10));
        put(238, new Point(-120,-120));
        put(239, new Point(-220,-30));
        put(240, new Point(-80,-120));
        put(241, new Point(220,50));
        put(242, new Point(160,-120));
        put(243, new Point(-220,10));
        put(244, new Point(-120,100));
        put(245, new Point(-80,100));
        put(246, new Point(220,-90));
        put(247, new Point(220,90));
        put(248, new Point(-220,50));
        put(249, new Point(160,100));
        put(250, new Point(-40,-120));
        put(251, new Point(200,-120));
        put(252, new Point(220,-50));
        put(253, new Point(-220,90));
        put(254, new Point(-220,-90));
        put(255, new Point(0,-120));
        put(256, new Point(220,-10));
        put(257, new Point(-200,-120));
        put(258, new Point(-40,100));
        put(259, new Point(200,100));
        put(260, new Point(-220,-50));
        put(261, new Point(0,100));
        put(262, new Point(220,30));
        put(263, new Point(-200,100));
    }};

}
