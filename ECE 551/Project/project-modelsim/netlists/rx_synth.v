
module rx_synth ( Clk_s, Rst_n, Core_Rcv_Ready, S_Data, RxData_Valid, RxData, 
        Rx_Ready, Rx_Error );
  output [31:0] RxData;
  input Clk_s, Rst_n, Core_Rcv_Ready, S_Data;
  output RxData_Valid, Rx_Ready, Rx_Error;
  wire   bitStuff, N22, N24, N25, N26, dataRx, bufferFull, N27, N229, N242,
         N243, n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13, n14,
         n15, n16, n17, n18, n19, n20, n21, n22, n23, n24, n25, n26, n27, n28,
         n29, n30, n31, n32, n33, n34, n35, n36, n37, n38, n39, n40, n41, n42,
         n43, n44, n45, n46, n47, n48, n49, n50, n51, n52, n53, n54, n55, n56,
         n57, n58, n59, n60, n61, n62, n63, n64, n65, n66, n67, n68, n69, n70,
         n71, n72, n73, n158, n159, n160, n161, n162, n163, n164, n165, n166,
         n167, n168, n169, n170, n171, n172, n173, n174, n175, n176, n177,
         n178, n179, n180, n181, n182, n183, n184, n185, n186, n187, n188,
         n189, n223, n224, n227, n228, n229, n230, n231, n232, n233, n234,
         n235, n236, n237, n238, n239, n240, n241, n242, n243, n244, n245,
         n246, n247, n248, n249, n250, n251, n252, n253, n254, n255, n256,
         n257, n258, n259, n260, n261, n262, n263, n264, n265, n266, n267,
         n268, n269, n270, n271, n272, n273, n274, n275, n276, n277, n278,
         n279, n280, n281, n282, n283, n284, n285, n286, n287, n288, n289,
         n290, n291, n292, n293, n294, n295, n296, n297, n298, n299, n300,
         n301, n302, n303, n304, n305, n306, n307, n308, n309, n310, n311,
         n312, n313, n314, n315, n316, n317, n318, n319, n320, n321, n322,
         n323, n324, n325, n326, n327, n328, n329, n330, n331, n332, n333,
         n334, n335, n336, n337, n338, n339, n340, n341, n342, n343, n344,
         n345, n346, n347, n348, n349, n350, n351, n352, n353, n354, n355,
         n356, n357, n358, n359, n360, n361, n362, n363, n364, n365, n366,
         n367, n368, n369;
  wire   [2:0] zeroCounter;
  wire   [5:0] receivedCounter;

  FD2QM1P \zeroCounter_reg[0]  ( .D(N24), .CP(Clk_s), .CD(Rst_n), .Q(
        zeroCounter[0]) );
  FD2QM1P \zeroCounter_reg[1]  ( .D(N25), .CP(Clk_s), .CD(Rst_n), .Q(
        zeroCounter[1]) );
  FD2QM1P \zeroCounter_reg[2]  ( .D(N26), .CP(Clk_s), .CD(Rst_n), .Q(
        zeroCounter[2]) );
  FD4QM1P startSeq_reg ( .D(n67), .CP(Clk_s), .SD(Rst_n), .Q(n68) );
  FD2QM1P bufferFull_reg ( .D(N242), .CP(Clk_s), .CD(Rst_n), .Q(bufferFull) );
  FD2QM1P \receivedCounter_reg[5]  ( .D(n69), .CP(Clk_s), .CD(Rst_n), .Q(
        receivedCounter[5]) );
  FD2QM1P bitStuff_reg ( .D(N22), .CP(Clk_s), .CD(Rst_n), .Q(bitStuff) );
  FD4QM1P \receivedPacket_reg[6]  ( .D(n53), .CP(Clk_s), .SD(Rst_n), .Q(n54)
         );
  FD4QM1P \receivedPacket_reg[12]  ( .D(n41), .CP(Clk_s), .SD(Rst_n), .Q(n42)
         );
  FD4QM1P \receivedPacket_reg[14]  ( .D(n37), .CP(Clk_s), .SD(Rst_n), .Q(n38)
         );
  FD4QM1P \receivedPacket_reg[15]  ( .D(n35), .CP(Clk_s), .SD(Rst_n), .Q(n36)
         );
  FD4QM1P \receivedPacket_reg[16]  ( .D(n33), .CP(Clk_s), .SD(Rst_n), .Q(n34)
         );
  FD4QM1P \receivedPacket_reg[27]  ( .D(n11), .CP(Clk_s), .SD(Rst_n), .Q(n12)
         );
  FD4QM1P \receivedPacket_reg[28]  ( .D(n9), .CP(Clk_s), .SD(Rst_n), .Q(n10)
         );
  FD4QM1P \receivedPacket_reg[30]  ( .D(n5), .CP(Clk_s), .SD(Rst_n), .Q(n6) );
  FD4QM1P \receivedPacket_reg[31]  ( .D(n3), .CP(Clk_s), .SD(Rst_n), .Q(n4) );
  FD2QM1P \RxData_reg[31]  ( .D(n158), .CP(Clk_s), .CD(Rst_n), .Q(RxData[31])
         );
  FD2QM1P \RxData_reg[30]  ( .D(n159), .CP(Clk_s), .CD(Rst_n), .Q(RxData[30])
         );
  FD2QM1P \RxData_reg[29]  ( .D(n160), .CP(Clk_s), .CD(Rst_n), .Q(RxData[29])
         );
  FD2QM1P \RxData_reg[28]  ( .D(n161), .CP(Clk_s), .CD(Rst_n), .Q(RxData[28])
         );
  FD2QM1P \RxData_reg[27]  ( .D(n162), .CP(Clk_s), .CD(Rst_n), .Q(RxData[27])
         );
  FD2QM1P \RxData_reg[26]  ( .D(n163), .CP(Clk_s), .CD(Rst_n), .Q(RxData[26])
         );
  FD2QM1P \RxData_reg[25]  ( .D(n164), .CP(Clk_s), .CD(Rst_n), .Q(RxData[25])
         );
  FD2QM1P \RxData_reg[24]  ( .D(n165), .CP(Clk_s), .CD(Rst_n), .Q(RxData[24])
         );
  FD2QM1P \RxData_reg[23]  ( .D(n166), .CP(Clk_s), .CD(Rst_n), .Q(RxData[23])
         );
  FD2QM1P \RxData_reg[22]  ( .D(n167), .CP(Clk_s), .CD(Rst_n), .Q(RxData[22])
         );
  FD2QM1P \RxData_reg[21]  ( .D(n168), .CP(Clk_s), .CD(Rst_n), .Q(RxData[21])
         );
  FD2QM1P \RxData_reg[20]  ( .D(n169), .CP(Clk_s), .CD(Rst_n), .Q(RxData[20])
         );
  FD2QM1P \RxData_reg[19]  ( .D(n170), .CP(Clk_s), .CD(Rst_n), .Q(RxData[19])
         );
  FD2QM1P \RxData_reg[18]  ( .D(n171), .CP(Clk_s), .CD(Rst_n), .Q(RxData[18])
         );
  FD2QM1P \RxData_reg[17]  ( .D(n172), .CP(Clk_s), .CD(Rst_n), .Q(RxData[17])
         );
  FD2QM1P \RxData_reg[16]  ( .D(n173), .CP(Clk_s), .CD(Rst_n), .Q(RxData[16])
         );
  FD2QM1P \RxData_reg[15]  ( .D(n174), .CP(Clk_s), .CD(Rst_n), .Q(RxData[15])
         );
  FD2QM1P \RxData_reg[14]  ( .D(n175), .CP(Clk_s), .CD(Rst_n), .Q(RxData[14])
         );
  FD2QM1P \RxData_reg[13]  ( .D(n176), .CP(Clk_s), .CD(Rst_n), .Q(RxData[13])
         );
  FD2QM1P \RxData_reg[12]  ( .D(n177), .CP(Clk_s), .CD(Rst_n), .Q(RxData[12])
         );
  FD2QM1P \RxData_reg[11]  ( .D(n178), .CP(Clk_s), .CD(Rst_n), .Q(RxData[11])
         );
  FD2QM1P \RxData_reg[10]  ( .D(n179), .CP(Clk_s), .CD(Rst_n), .Q(RxData[10])
         );
  FD2QM1P \RxData_reg[9]  ( .D(n180), .CP(Clk_s), .CD(Rst_n), .Q(RxData[9]) );
  FD2QM1P \RxData_reg[8]  ( .D(n181), .CP(Clk_s), .CD(Rst_n), .Q(RxData[8]) );
  FD2QM1P \RxData_reg[7]  ( .D(n182), .CP(Clk_s), .CD(Rst_n), .Q(RxData[7]) );
  FD2QM1P \RxData_reg[6]  ( .D(n183), .CP(Clk_s), .CD(Rst_n), .Q(RxData[6]) );
  FD2QM1P \RxData_reg[5]  ( .D(n184), .CP(Clk_s), .CD(Rst_n), .Q(RxData[5]) );
  FD2QM1P \RxData_reg[4]  ( .D(n185), .CP(Clk_s), .CD(Rst_n), .Q(RxData[4]) );
  FD2QM1P \RxData_reg[3]  ( .D(n186), .CP(Clk_s), .CD(Rst_n), .Q(RxData[3]) );
  FD2QM1P \RxData_reg[2]  ( .D(n187), .CP(Clk_s), .CD(Rst_n), .Q(RxData[2]) );
  FD2QM1P \RxData_reg[0]  ( .D(n189), .CP(Clk_s), .CD(Rst_n), .Q(RxData[0]) );
  FD2QM1P dataRx_reg ( .D(n224), .CP(Clk_s), .CD(Rst_n), .Q(dataRx) );
  FD2QM1P Rx_Error_reg ( .D(N229), .CP(Clk_s), .CD(Rst_n), .Q(Rx_Error) );
  FD2QM1P Rx_Ready_reg ( .D(N27), .CP(Clk_s), .CD(Rst_n), .Q(Rx_Ready) );
  FD2QM1P \receivedCounter_reg[4]  ( .D(n70), .CP(Clk_s), .CD(Rst_n), .Q(
        receivedCounter[4]) );
  FD2QM1P \receivedCounter_reg[3]  ( .D(n71), .CP(Clk_s), .CD(Rst_n), .Q(
        receivedCounter[3]) );
  FD2QM1P \RxData_reg[1]  ( .D(n188), .CP(Clk_s), .CD(Rst_n), .Q(RxData[1]) );
  FD4QM1P \receivedPacket_reg[20]  ( .D(n25), .CP(Clk_s), .SD(Rst_n), .Q(n26)
         );
  FD4QM1P \receivedPacket_reg[29]  ( .D(n7), .CP(Clk_s), .SD(Rst_n), .Q(n8) );
  FD4QM3P \receivedPacket_reg[1]  ( .D(n63), .CP(Clk_s), .SD(Rst_n), .Q(n64)
         );
  FD4QM3P \receivedPacket_reg[17]  ( .D(n31), .CP(Clk_s), .SD(Rst_n), .Q(n32)
         );
  FD4QM3P \receivedPacket_reg[9]  ( .D(n47), .CP(Clk_s), .SD(Rst_n), .Q(n48)
         );
  FD4QM3P \receivedPacket_reg[2]  ( .D(n61), .CP(Clk_s), .SD(Rst_n), .Q(n62)
         );
  FD4QM3P \receivedPacket_reg[5]  ( .D(n55), .CP(Clk_s), .SD(Rst_n), .Q(n56)
         );
  FD4QM3P \receivedPacket_reg[18]  ( .D(n29), .CP(Clk_s), .SD(Rst_n), .Q(n30)
         );
  FD4QM3P \receivedPacket_reg[21]  ( .D(n23), .CP(Clk_s), .SD(Rst_n), .Q(n24)
         );
  FD4QM3P \receivedPacket_reg[3]  ( .D(n59), .CP(Clk_s), .SD(Rst_n), .Q(n60)
         );
  FD4QM3P \receivedPacket_reg[25]  ( .D(n15), .CP(Clk_s), .SD(Rst_n), .Q(n16)
         );
  FD4QM3P \receivedPacket_reg[19]  ( .D(n27), .CP(Clk_s), .SD(Rst_n), .Q(n28)
         );
  FD2QM3P \receivedCounter_reg[2]  ( .D(n72), .CP(Clk_s), .CD(Rst_n), .Q(
        receivedCounter[2]) );
  FD4QM3P \receivedPacket_reg[10]  ( .D(n45), .CP(Clk_s), .SD(Rst_n), .Q(n46)
         );
  FD4QM3P \receivedPacket_reg[13]  ( .D(n39), .CP(Clk_s), .SD(Rst_n), .Q(n40)
         );
  FD4QM8P \receivedPacket_reg[0]  ( .D(n65), .CP(Clk_s), .SD(Rst_n), .Q(n66)
         );
  FD4QM3P \receivedPacket_reg[11]  ( .D(n43), .CP(Clk_s), .SD(Rst_n), .Q(n44)
         );
  FD4QM3P \receivedPacket_reg[26]  ( .D(n13), .CP(Clk_s), .SD(Rst_n), .Q(n14)
         );
  FD2QM8P RxData_Valid_reg ( .D(N243), .CP(Clk_s), .CD(Rst_n), .Q(RxData_Valid) );
  FD2QM3P \receivedCounter_reg[0]  ( .D(n223), .CP(Clk_s), .CD(Rst_n), .Q(
        receivedCounter[0]) );
  FD2QM3P \receivedCounter_reg[1]  ( .D(n73), .CP(Clk_s), .CD(Rst_n), .Q(
        receivedCounter[1]) );
  FD4QM3P \receivedPacket_reg[32]  ( .D(n1), .CP(Clk_s), .SD(Rst_n), .Q(n2) );
  FD4QM1P \receivedPacket_reg[7]  ( .D(n51), .CP(Clk_s), .SD(Rst_n), .Q(n52)
         );
  FD4QM1P \receivedPacket_reg[8]  ( .D(n49), .CP(Clk_s), .SD(Rst_n), .Q(n50)
         );
  FD4QM1P \receivedPacket_reg[22]  ( .D(n21), .CP(Clk_s), .SD(Rst_n), .Q(n22)
         );
  FD4QM1P \receivedPacket_reg[23]  ( .D(n19), .CP(Clk_s), .SD(Rst_n), .Q(n20)
         );
  FD4QM1P \receivedPacket_reg[24]  ( .D(n17), .CP(Clk_s), .SD(Rst_n), .Q(n18)
         );
  FD4QM1P \receivedPacket_reg[4]  ( .D(n57), .CP(Clk_s), .SD(Rst_n), .Q(n58)
         );
  ENFM1P U238 ( .A(n16), .B(n14), .Z(n285) );
  ENFM1P U239 ( .A(n48), .B(n46), .Z(n272) );
  ENFM1P U240 ( .A(n64), .B(n62), .Z(n269) );
  AOI21M2P U241 ( .A(n247), .B(n266), .C(n250), .Z(n262) );
  N1M3P U242 ( .A(n246), .Z(n244) );
  BUFM1P U243 ( .A(n359), .Z(n253) );
  BUFM2P U244 ( .A(n359), .Z(n252) );
  BUFM3P U245 ( .A(n356), .Z(n248) );
  BUFM2P U246 ( .A(n359), .Z(n251) );
  OAI22CDNM1P U247 ( .A(n50), .B(n244), .C(n234), .D(RxData[7]), .Z(n182) );
  OAI22CDNM1P U248 ( .A(n48), .B(n244), .C(n234), .D(RxData[8]), .Z(n181) );
  AOI21M1P U249 ( .A(n366), .B(RxData_Valid), .C(bufferFull), .Z(n227) );
  NR2BNM1P U250 ( .B(Core_Rcv_Ready), .A(n227), .Z(N243) );
  OAI22CDNM1P U251 ( .A(n46), .B(n244), .C(n234), .D(RxData[9]), .Z(n180) );
  AOI21M1P U252 ( .A(receivedCounter[1]), .B(receivedCounter[0]), .C(
        receivedCounter[2]), .Z(n228) );
  AO21M1P U253 ( .A(receivedCounter[2]), .B(receivedCounter[1]), .C(n228), .Z(
        n229) );
  N1M1P U254 ( .A(receivedCounter[2]), .Z(n230) );
  OAI22M1P U255 ( .A(n294), .B(n229), .C(n262), .D(n230), .Z(n72) );
  OAI22CDNM1P U256 ( .A(n62), .B(n245), .C(n234), .D(RxData[1]), .Z(n188) );
  OAI22CDNM1P U257 ( .A(n44), .B(n244), .C(n234), .D(RxData[10]), .Z(n179) );
  ENFM1P U258 ( .A(n56), .B(n54), .Z(n231) );
  EN3M1P U259 ( .A(n50), .B(n52), .C(n231), .Z(n242) );
  N1M1P U260 ( .A(n264), .Z(n232) );
  ND3M1P U261 ( .A(n263), .B(n249), .C(n265), .Z(n233) );
  OAI21M1P U262 ( .A(n265), .B(n232), .C(n233), .Z(n71) );
  OAI22CDNM1P U263 ( .A(n32), .B(n234), .C(n245), .D(RxData[16]), .Z(n173) );
  OAI22CDNM1P U264 ( .A(n26), .B(n244), .C(n234), .D(RxData[19]), .Z(n170) );
  N1M1P U265 ( .A(bufferFull), .Z(n293) );
  N1M12P U266 ( .A(n241), .Z(n234) );
  N1M4P U267 ( .A(n295), .Z(n359) );
  N1M3P U268 ( .A(n294), .Z(n356) );
  N1M1P U269 ( .A(n325), .Z(n235) );
  ND2M3P U270 ( .A(n323), .B(n296), .Z(n322) );
  N1M2P U271 ( .A(n322), .Z(n246) );
  AND2M10P U272 ( .A(n323), .B(n296), .Z(n241) );
  EOFM1P U273 ( .A(n283), .B(n282), .Z(n237) );
  ENFM1P U274 ( .A(n270), .B(n242), .Z(n236) );
  EOFM2P U275 ( .A(n236), .B(n277), .Z(n291) );
  NR2BNM10P U276 ( .B(n293), .A(n292), .Z(n323) );
  EOFM2P U277 ( .A(n237), .B(n289), .Z(n290) );
  ENFM4P U278 ( .A(n291), .B(n290), .Z(n292) );
  N1M8P U279 ( .A(n241), .Z(n245) );
  ND2BNM2P U280 ( .B(n324), .A(n240), .Z(n366) );
  NR2FM2P U281 ( .A(receivedCounter[4]), .B(receivedCounter[2]), .Z(n254) );
  EOFM2P U282 ( .A(n4), .B(n238), .Z(n286) );
  EOFM4P U283 ( .A(n66), .B(n2), .Z(n238) );
  BUFM1P U284 ( .A(n323), .Z(n240) );
  BUFM8P U285 ( .A(n322), .Z(n239) );
  OR2M2P U286 ( .A(receivedCounter[1]), .B(receivedCounter[0]), .Z(n255) );
  ND3M2P U287 ( .A(n257), .B(n68), .C(dataRx), .Z(n259) );
  ND2M2P U288 ( .A(n324), .B(n258), .Z(n294) );
  ND2BNM1P U289 ( .B(receivedCounter[3]), .A(n254), .Z(n256) );
  MUX21LFM1P U290 ( .A(n64), .B(n297), .S(n234), .Z(n189) );
  AOI22M3P U291 ( .A(bufferFull), .B(n296), .C(n68), .D(n260), .Z(n295) );
  NR2FM2P U292 ( .A(n324), .B(n259), .Z(n296) );
  BUFM3P U293 ( .A(n359), .Z(n250) );
  BUFM2P U294 ( .A(n356), .Z(n249) );
  N1M1P U295 ( .A(bitStuff), .Z(n257) );
  OAI21M3P U296 ( .A(n256), .B(n255), .C(receivedCounter[5]), .Z(n324) );
  N1M1P U297 ( .A(n60), .Z(n354) );
  N1M1P U298 ( .A(n52), .Z(n350) );
  N1M1P U299 ( .A(n50), .Z(n349) );
  N1M1P U300 ( .A(n56), .Z(n352) );
  N1M1P U301 ( .A(n48), .Z(n348) );
  N1M1P U302 ( .A(n62), .Z(n355) );
  N1M1P U303 ( .A(n64), .Z(n357) );
  N1M1P U304 ( .A(n32), .Z(n340) );
  N1M1P U305 ( .A(n30), .Z(n339) );
  N1M1P U306 ( .A(n28), .Z(n338) );
  N1M1P U307 ( .A(n46), .Z(n347) );
  N1M1P U308 ( .A(n44), .Z(n346) );
  N1M1P U309 ( .A(n42), .Z(n345) );
  N1M1P U310 ( .A(n40), .Z(n344) );
  N1M1P U311 ( .A(n38), .Z(n343) );
  N1M1P U312 ( .A(n36), .Z(n342) );
  N1M1P U313 ( .A(n34), .Z(n341) );
  N1M1P U314 ( .A(n26), .Z(n337) );
  N1M1P U315 ( .A(n22), .Z(n335) );
  N1M1P U316 ( .A(n20), .Z(n334) );
  N1M1P U317 ( .A(n18), .Z(n333) );
  N1M1P U318 ( .A(n16), .Z(n332) );
  N1M1P U319 ( .A(n12), .Z(n330) );
  N1M1P U320 ( .A(n10), .Z(n329) );
  N1M1P U321 ( .A(n8), .Z(n328) );
  N1M1P U322 ( .A(n6), .Z(n327) );
  N1M1P U323 ( .A(n4), .Z(n326) );
  AND2M1P U324 ( .A(dataRx), .B(bitStuff), .Z(n260) );
  BUFM3P U325 ( .A(n356), .Z(n247) );
  N1M1P U326 ( .A(n54), .Z(n351) );
  ND2BNM1P U327 ( .B(n358), .A(n295), .Z(n224) );
  AOI22M1P U328 ( .A(n325), .B(n253), .C(n249), .D(n326), .Z(n1) );
  AOI22M1P U329 ( .A(n360), .B(n251), .C(S_Data), .D(n358), .Z(n65) );
  ND2M1P U330 ( .A(n294), .B(n68), .Z(n358) );
  AOI22M1P U331 ( .A(n350), .B(n250), .C(n247), .D(n351), .Z(n51) );
  AOI22M1P U332 ( .A(n354), .B(n250), .C(n247), .D(n355), .Z(n59) );
  AOI22M1P U333 ( .A(n353), .B(n250), .C(n247), .D(n354), .Z(n57) );
  AOI22M1P U334 ( .A(n349), .B(n250), .C(n247), .D(n350), .Z(n49) );
  AOI22M1P U335 ( .A(n351), .B(n250), .C(n247), .D(n352), .Z(n53) );
  AOI22M1P U336 ( .A(n348), .B(n250), .C(n247), .D(n349), .Z(n47) );
  AOI22M1P U337 ( .A(n352), .B(n250), .C(n247), .D(n353), .Z(n55) );
  AOI22M1P U338 ( .A(n347), .B(n251), .C(n247), .D(n348), .Z(n45) );
  AOI22M1P U339 ( .A(n355), .B(n250), .C(n247), .D(n357), .Z(n61) );
  NR3CNM1P U340 ( .C(n296), .A(n240), .B(bufferFull), .Z(N229) );
  MUX21LM1P U341 ( .A(n253), .B(n249), .S(n266), .Z(n267) );
  ND2M1P U342 ( .A(n366), .B(n365), .Z(N242) );
  OAI21M1P U343 ( .A(n364), .B(Core_Rcv_Ready), .C(bufferFull), .Z(n365) );
  N1M1P U344 ( .A(RxData_Valid), .Z(n364) );
  MUX21LM1P U345 ( .A(n361), .B(n363), .S(receivedCounter[4]), .Z(n70) );
  OAI22M1P U346 ( .A(n363), .B(n369), .C(n362), .D(n361), .Z(n69) );
  ND3M1P U347 ( .A(n249), .B(receivedCounter[3]), .C(n263), .Z(n361) );
  N1M1P U348 ( .A(receivedCounter[4]), .Z(n362) );
  N1M1P U349 ( .A(receivedCounter[5]), .Z(n369) );
  AOI21M1P U350 ( .A(n247), .B(n265), .C(n264), .Z(n363) );
  N1M1P U351 ( .A(receivedCounter[3]), .Z(n265) );
  AOI22M1P U352 ( .A(n328), .B(n253), .C(n248), .D(n329), .Z(n7) );
  AOI22M1P U353 ( .A(n337), .B(n252), .C(n248), .D(n338), .Z(n25) );
  AOI22M1P U354 ( .A(n339), .B(n251), .C(n248), .D(n340), .Z(n29) );
  AOI22M1P U355 ( .A(n332), .B(n252), .C(n248), .D(n333), .Z(n15) );
  AOI22M1P U356 ( .A(n357), .B(n250), .C(n248), .D(n360), .Z(n63) );
  AOI22M1P U357 ( .A(n340), .B(n251), .C(n248), .D(n341), .Z(n31) );
  AOI22M1P U358 ( .A(n338), .B(n252), .C(n248), .D(n339), .Z(n27) );
  AOI22M1P U359 ( .A(n346), .B(n251), .C(n248), .D(n347), .Z(n43) );
  AOI22M1P U360 ( .A(n345), .B(n251), .C(n248), .D(n346), .Z(n41) );
  AOI22M1P U361 ( .A(n344), .B(n251), .C(n248), .D(n345), .Z(n39) );
  AOI22M1P U362 ( .A(n343), .B(n251), .C(n248), .D(n344), .Z(n37) );
  AOI22M1P U363 ( .A(n342), .B(n251), .C(n248), .D(n343), .Z(n35) );
  AOI22M1P U364 ( .A(n341), .B(n251), .C(n248), .D(n342), .Z(n33) );
  AOI22M1P U365 ( .A(n336), .B(n252), .C(n248), .D(n337), .Z(n23) );
  AOI22M1P U366 ( .A(n335), .B(n252), .C(n248), .D(n336), .Z(n21) );
  AOI22M1P U367 ( .A(n334), .B(n252), .C(n248), .D(n335), .Z(n19) );
  AOI22M1P U368 ( .A(n333), .B(n252), .C(n248), .D(n334), .Z(n17) );
  AOI22M1P U369 ( .A(n331), .B(n252), .C(n248), .D(n332), .Z(n13) );
  AOI22M1P U370 ( .A(n330), .B(n252), .C(n248), .D(n331), .Z(n11) );
  AOI22M1P U371 ( .A(n329), .B(n252), .C(n248), .D(n330), .Z(n9) );
  AOI22M1P U372 ( .A(n327), .B(n253), .C(n248), .D(n328), .Z(n5) );
  AOI22M1P U373 ( .A(n326), .B(n253), .C(n248), .D(n327), .Z(n3) );
  N1M1P U374 ( .A(RxData[0]), .Z(n297) );
  ND2M1P U375 ( .A(n245), .B(RxData[15]), .Z(n307) );
  MUX21LM1P U376 ( .A(n261), .B(n262), .S(receivedCounter[1]), .Z(n73) );
  N1M1P U377 ( .A(n259), .Z(n258) );
  OAI21M1P U378 ( .A(n14), .B(n239), .C(n315), .Z(n164) );
  ND2M1P U379 ( .A(n245), .B(RxData[25]), .Z(n315) );
  OAI21M1P U380 ( .A(n24), .B(n239), .C(n310), .Z(n169) );
  ND2M1P U381 ( .A(n245), .B(RxData[20]), .Z(n310) );
  OAI21M1P U382 ( .A(n16), .B(n239), .C(n314), .Z(n165) );
  ND2M1P U383 ( .A(n245), .B(RxData[24]), .Z(n314) );
  OAI21M1P U384 ( .A(n4), .B(n239), .C(n320), .Z(n159) );
  ND2M1P U385 ( .A(n245), .B(RxData[30]), .Z(n320) );
  OAI21M1P U386 ( .A(n36), .B(n239), .C(n306), .Z(n175) );
  ND2M1P U387 ( .A(n245), .B(RxData[14]), .Z(n306) );
  OAI21M1P U388 ( .A(n28), .B(n239), .C(n309), .Z(n171) );
  ND2M1P U389 ( .A(n245), .B(RxData[18]), .Z(n309) );
  OAI21M1P U390 ( .A(n20), .B(n239), .C(n312), .Z(n167) );
  ND2M1P U391 ( .A(n245), .B(RxData[22]), .Z(n312) );
  OAI21M1P U392 ( .A(n38), .B(n239), .C(n305), .Z(n176) );
  ND2M1P U393 ( .A(n245), .B(RxData[13]), .Z(n305) );
  OAI21M1P U394 ( .A(n42), .B(n239), .C(n303), .Z(n178) );
  ND2M1P U395 ( .A(n245), .B(RxData[11]), .Z(n303) );
  OAI21M1P U396 ( .A(n10), .B(n239), .C(n317), .Z(n162) );
  ND2M1P U397 ( .A(n245), .B(RxData[27]), .Z(n317) );
  OAI21M1P U398 ( .A(n6), .B(n239), .C(n319), .Z(n160) );
  ND2M1P U399 ( .A(n245), .B(RxData[29]), .Z(n319) );
  OAI21M1P U400 ( .A(n60), .B(n244), .C(n298), .Z(n187) );
  ND2M1P U401 ( .A(n234), .B(RxData[2]), .Z(n298) );
  OAI21M1P U402 ( .A(n12), .B(n239), .C(n316), .Z(n163) );
  ND2M1P U403 ( .A(n234), .B(RxData[26]), .Z(n316) );
  OAI21M1P U404 ( .A(n40), .B(n239), .C(n304), .Z(n177) );
  ND2M1P U405 ( .A(n234), .B(RxData[12]), .Z(n304) );
  OAI21M1P U406 ( .A(n56), .B(n244), .C(n300), .Z(n185) );
  ND2M1P U407 ( .A(n234), .B(RxData[4]), .Z(n300) );
  OAI21M1P U408 ( .A(n52), .B(n244), .C(n302), .Z(n183) );
  ND2M1P U409 ( .A(n234), .B(RxData[6]), .Z(n302) );
  OAI21M1P U410 ( .A(n22), .B(n239), .C(n311), .Z(n168) );
  ND2M1P U411 ( .A(n234), .B(RxData[21]), .Z(n311) );
  OAI21M1P U412 ( .A(n18), .B(n239), .C(n313), .Z(n166) );
  ND2M1P U413 ( .A(n234), .B(RxData[23]), .Z(n313) );
  OAI21M1P U414 ( .A(n235), .B(n239), .C(n321), .Z(n158) );
  ND2M1P U415 ( .A(n234), .B(RxData[31]), .Z(n321) );
  OAI21M1P U416 ( .A(n8), .B(n239), .C(n318), .Z(n161) );
  ND2M1P U417 ( .A(n234), .B(RxData[28]), .Z(n318) );
  ND2M1P U418 ( .A(n234), .B(RxData[3]), .Z(n299) );
  ND2M1P U419 ( .A(n234), .B(RxData[17]), .Z(n308) );
  ENFM1P U420 ( .A(n288), .B(n287), .Z(n289) );
  EOFM1P U421 ( .A(n243), .B(n286), .Z(n287) );
  ENFM1P U422 ( .A(n8), .B(n6), .Z(n243) );
  ENFM1P U423 ( .A(n285), .B(n284), .Z(n288) );
  EOFM1P U424 ( .A(n12), .B(n10), .Z(n284) );
  ENFM1P U425 ( .A(n281), .B(n280), .Z(n282) );
  ENFM1P U426 ( .A(n20), .B(n18), .Z(n280) );
  ENFM1P U427 ( .A(n24), .B(n22), .Z(n281) );
  ENFM1P U428 ( .A(n279), .B(n278), .Z(n283) );
  EOFM1P U429 ( .A(n28), .B(n26), .Z(n278) );
  ENFM1P U430 ( .A(n32), .B(n30), .Z(n279) );
  ENFM1P U431 ( .A(n276), .B(n275), .Z(n277) );
  ENFM1P U432 ( .A(n274), .B(n273), .Z(n275) );
  EOFM1P U433 ( .A(n36), .B(n34), .Z(n273) );
  ENFM1P U434 ( .A(n40), .B(n38), .Z(n274) );
  ENFM1P U435 ( .A(n272), .B(n271), .Z(n276) );
  EOFM1P U436 ( .A(n44), .B(n42), .Z(n271) );
  ENFM1P U437 ( .A(n269), .B(n268), .Z(n270) );
  EOFM1P U438 ( .A(n60), .B(n58), .Z(n268) );
  N1M1P U439 ( .A(n66), .Z(n360) );
  OAI21M1P U440 ( .A(n30), .B(n234), .C(n308), .Z(n172) );
  ND2M1P U441 ( .A(n234), .B(RxData[5]), .Z(n301) );
  OAI21M1P U442 ( .A(n34), .B(n245), .C(n307), .Z(n174) );
  OAI21M1P U443 ( .A(n263), .B(n294), .C(n295), .Z(n264) );
  N1M1P U444 ( .A(n58), .Z(n353) );
  OAI21M1P U445 ( .A(n58), .B(n244), .C(n299), .Z(n186) );
  AO21CNM1P U446 ( .A(n351), .B(n246), .C(n301), .Z(n184) );
  ND2M1P U447 ( .A(n267), .B(n68), .Z(n223) );
  N1M1P U448 ( .A(n24), .Z(n336) );
  N1M1P U449 ( .A(n14), .Z(n331) );
  AND3M1P U450 ( .A(receivedCounter[0]), .B(receivedCounter[1]), .C(
        receivedCounter[2]), .Z(n263) );
  N1M1P U451 ( .A(receivedCounter[0]), .Z(n266) );
  ND2M1P U452 ( .A(n249), .B(receivedCounter[0]), .Z(n261) );
  N1M1P U453 ( .A(n2), .Z(n325) );
  ND2M1P U454 ( .A(zeroCounter[1]), .B(zeroCounter[0]), .Z(n367) );
  NR3M1P U455 ( .A(S_Data), .B(zeroCounter[2]), .C(n367), .Z(N26) );
  AND2M1P U456 ( .A(N26), .B(n369), .Z(N22) );
  NR3M1P U457 ( .A(S_Data), .B(zeroCounter[2]), .C(zeroCounter[0]), .Z(N24) );
  OAI21M1P U458 ( .A(zeroCounter[1]), .B(zeroCounter[0]), .C(n367), .Z(n368)
         );
  NR3M1P U459 ( .A(S_Data), .B(zeroCounter[2]), .C(n368), .Z(N25) );
  NR2BNM1P U460 ( .B(n68), .A(dataRx), .Z(N27) );
  ND2BNM1P U461 ( .B(S_Data), .A(zeroCounter[2]), .Z(n67) );
endmodule

