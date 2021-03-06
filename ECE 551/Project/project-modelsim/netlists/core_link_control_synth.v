
module Packet_Queue_0 ( Clk_r, Rst_n, Packet_In, Read_Ack, Write_Ack, 
        Packet_Out, Empty, Full );
  input [31:0] Packet_In;
  output [31:0] Packet_Out;
  input Clk_r, Rst_n, Read_Ack, Write_Ack;
  output Empty, Full;
  wire   \Queue[4][31] , \Queue[4][30] , \Queue[4][29] , \Queue[4][28] ,
         \Queue[4][27] , \Queue[4][26] , \Queue[4][25] , \Queue[4][24] ,
         \Queue[4][23] , \Queue[4][22] , \Queue[4][21] , \Queue[4][20] ,
         \Queue[4][19] , \Queue[4][18] , \Queue[4][17] , \Queue[4][16] ,
         \Queue[4][15] , \Queue[4][14] , \Queue[4][13] , \Queue[4][12] ,
         \Queue[4][11] , \Queue[4][10] , \Queue[4][9] , \Queue[4][8] ,
         \Queue[4][7] , \Queue[4][6] , \Queue[4][5] , \Queue[4][4] ,
         \Queue[4][3] , \Queue[4][2] , \Queue[4][1] , \Queue[4][0] ,
         \Queue[3][31] , \Queue[3][30] , \Queue[3][29] , \Queue[3][28] ,
         \Queue[3][27] , \Queue[3][26] , \Queue[3][25] , \Queue[3][24] ,
         \Queue[3][23] , \Queue[3][22] , \Queue[3][21] , \Queue[3][20] ,
         \Queue[3][19] , \Queue[3][18] , \Queue[3][17] , \Queue[3][16] ,
         \Queue[3][15] , \Queue[3][14] , \Queue[3][13] , \Queue[3][12] ,
         \Queue[3][11] , \Queue[3][10] , \Queue[3][9] , \Queue[3][8] ,
         \Queue[3][7] , \Queue[3][6] , \Queue[3][5] , \Queue[3][4] ,
         \Queue[3][3] , \Queue[3][2] , \Queue[3][1] , \Queue[3][0] ,
         \Queue[2][31] , \Queue[2][30] , \Queue[2][29] , \Queue[2][28] ,
         \Queue[2][27] , \Queue[2][26] , \Queue[2][25] , \Queue[2][24] ,
         \Queue[2][23] , \Queue[2][22] , \Queue[2][21] , \Queue[2][20] ,
         \Queue[2][19] , \Queue[2][18] , \Queue[2][17] , \Queue[2][16] ,
         \Queue[2][15] , \Queue[2][14] , \Queue[2][13] , \Queue[2][12] ,
         \Queue[2][11] , \Queue[2][10] , \Queue[2][9] , \Queue[2][8] ,
         \Queue[2][7] , \Queue[2][6] , \Queue[2][5] , \Queue[2][4] ,
         \Queue[2][3] , \Queue[2][2] , \Queue[2][1] , \Queue[2][0] ,
         \Queue[1][31] , \Queue[1][30] , \Queue[1][29] , \Queue[1][28] ,
         \Queue[1][27] , \Queue[1][26] , \Queue[1][25] , \Queue[1][24] ,
         \Queue[1][23] , \Queue[1][22] , \Queue[1][21] , \Queue[1][20] ,
         \Queue[1][19] , \Queue[1][18] , \Queue[1][17] , \Queue[1][16] ,
         \Queue[1][15] , \Queue[1][14] , \Queue[1][13] , \Queue[1][12] ,
         \Queue[1][11] , \Queue[1][10] , \Queue[1][9] , \Queue[1][8] ,
         \Queue[1][7] , \Queue[1][6] , \Queue[1][5] , \Queue[1][4] ,
         \Queue[1][3] , \Queue[1][2] , \Queue[1][1] , \Queue[1][0] , n1, n2,
         n209, n210, n211, n212, n213, n214, n215, n216, n217, n218, n219,
         n220, n221, n222, n223, n224, n225, n226, n227, n228, n229, n230,
         n231, n232, n233, n234, n235, n236, n237, n238, n239, n240, n241,
         n242, n243, n244, n245, n246, n247, n248, n249, n250, n251, n252,
         n253, n254, n255, n256, n257, n258, n259, n260, n261, n262, n263,
         n264, n265, n266, n267, n268, n269, n270, n271, n272, n273, n274,
         n275, n276, n277, n278, n279, n280, n281, n282, n283, n284, n285,
         n286, n287, n288, n289, n290, n291, n292, n293, n294, n295, n296,
         n297, n298, n299, n300, n301, n302, n303, n304, n305, n306, n307,
         n308, n309, n310, n311, n312, n313, n314, n315, n316, n317, n318,
         n319, n320, n321, n322, n323, n324, n325, n326, n327, n328, n329,
         n330, n331, n332, n333, n334, n335, n336, n337, n338, n339, n340,
         n341, n342, n343, n344, n345, n346, n347, n348, n349, n350, n351,
         n352, n353, n354, n355, n356, n357, n358, n359, n360, n361, n362,
         n363, n364, n365, n366, n367, n368, n369, n370, n371, n372, n3, n4,
         n5, n6, n7, n8, n9, n10, n11, n12, n13, n14, n15, n16, n17, n18, n19,
         n20, n21, n22, n23, n24, n25, n26, n27, n28, n29, n30, n31, n32, n33,
         n34, n35, n36, n37, n38, n39, n40, n41, n42, n43, n44, n45, n46, n47,
         n48, n49, n50, n51, n52, n53, n54, n55, n56, n57, n58, n59, n60, n61,
         n62, n63, n64, n65, n66, n67, n68, n69, n70, n71, n72, n73, n74, n75,
         n76, n77, n78, n79, n80, n81, n82, n83, n84, n85, n86, n87, n88, n89,
         n90, n91, n92, n93, n94, n95, n96, n97, n98, n99, n100, n101, n102,
         n103, n104, n105, n106, n107, n108, n109, n110, n111, n112, n113,
         n114, n115, n116, n117, n118, n119, n120, n121, n122, n123, n124,
         n125, n126, n127, n128, n129, n130, n131, n132, n133, n134, n135,
         n136, n137, n138, n139, n140, n141, n142, n143, n144, n145, n146,
         n147, n148, n149, n150, n151, n152, n153, n154, n155, n156, n157,
         n158, n159, n160, n161, n162, n163, n164, n165, n166, n167, n168,
         n169, n170, n171, n172, n173, n174, n175, n176, n177, n178, n179,
         n180, n181, n182, n183, n184, n185, n186, n187, n188, n189, n190,
         n191, n192, n193, n194, n195, n196, n197, n198, n199, n200, n201,
         n202, n203, n204, n205, n206, n207, n208, n373, n374, n375, n376;
  wire   [2:0] Count;

  FDN2QM1P \Count_reg[0]  ( .D(n372), .CPN(Clk_r), .CD(Rst_n), .Q(Count[0]) );
  FDN2QM1P \Count_reg[1]  ( .D(n371), .CPN(Clk_r), .CD(Rst_n), .Q(Count[1]) );
  FDN2QM1P \Count_reg[2]  ( .D(n370), .CPN(Clk_r), .CD(Rst_n), .Q(Count[2]) );
  FDN1QM1P \Queue_reg[4][30]  ( .D(n338), .CPN(Clk_r), .Q(\Queue[4][30] ) );
  FDN1QM1P \Queue_reg[3][30]  ( .D(n210), .CPN(Clk_r), .Q(\Queue[3][30] ) );
  FDN1QM1P \Queue_reg[2][30]  ( .D(n242), .CPN(Clk_r), .Q(\Queue[2][30] ) );
  FDN1QM1P \Queue_reg[4][29]  ( .D(n339), .CPN(Clk_r), .Q(\Queue[4][29] ) );
  FDN1QM1P \Queue_reg[3][29]  ( .D(n211), .CPN(Clk_r), .Q(\Queue[3][29] ) );
  FDN1QM1P \Queue_reg[2][29]  ( .D(n243), .CPN(Clk_r), .Q(\Queue[2][29] ) );
  FDN1QM1P \Queue_reg[4][28]  ( .D(n340), .CPN(Clk_r), .Q(\Queue[4][28] ) );
  FDN1QM1P \Queue_reg[3][28]  ( .D(n212), .CPN(Clk_r), .Q(\Queue[3][28] ) );
  FDN1QM1P \Queue_reg[2][28]  ( .D(n244), .CPN(Clk_r), .Q(\Queue[2][28] ) );
  FDN1QM1P \Queue_reg[4][27]  ( .D(n341), .CPN(Clk_r), .Q(\Queue[4][27] ) );
  FDN1QM1P \Queue_reg[3][27]  ( .D(n213), .CPN(Clk_r), .Q(\Queue[3][27] ) );
  FDN1QM1P \Queue_reg[2][27]  ( .D(n245), .CPN(Clk_r), .Q(\Queue[2][27] ) );
  FDN1QM1P \Queue_reg[4][26]  ( .D(n342), .CPN(Clk_r), .Q(\Queue[4][26] ) );
  FDN1QM1P \Queue_reg[3][26]  ( .D(n214), .CPN(Clk_r), .Q(\Queue[3][26] ) );
  FDN1QM1P \Queue_reg[2][26]  ( .D(n246), .CPN(Clk_r), .Q(\Queue[2][26] ) );
  FDN1QM1P \Queue_reg[4][25]  ( .D(n343), .CPN(Clk_r), .Q(\Queue[4][25] ) );
  FDN1QM1P \Queue_reg[3][25]  ( .D(n215), .CPN(Clk_r), .Q(\Queue[3][25] ) );
  FDN1QM1P \Queue_reg[2][25]  ( .D(n247), .CPN(Clk_r), .Q(\Queue[2][25] ) );
  FDN1QM1P \Queue_reg[4][24]  ( .D(n344), .CPN(Clk_r), .Q(\Queue[4][24] ) );
  FDN1QM1P \Queue_reg[3][24]  ( .D(n216), .CPN(Clk_r), .Q(\Queue[3][24] ) );
  FDN1QM1P \Queue_reg[2][24]  ( .D(n248), .CPN(Clk_r), .Q(\Queue[2][24] ) );
  FDN1QM1P \Queue_reg[4][23]  ( .D(n345), .CPN(Clk_r), .Q(\Queue[4][23] ) );
  FDN1QM1P \Queue_reg[3][23]  ( .D(n217), .CPN(Clk_r), .Q(\Queue[3][23] ) );
  FDN1QM1P \Queue_reg[2][23]  ( .D(n249), .CPN(Clk_r), .Q(\Queue[2][23] ) );
  FDN1QM1P \Queue_reg[4][22]  ( .D(n346), .CPN(Clk_r), .Q(\Queue[4][22] ) );
  FDN1QM1P \Queue_reg[3][22]  ( .D(n218), .CPN(Clk_r), .Q(\Queue[3][22] ) );
  FDN1QM1P \Queue_reg[2][22]  ( .D(n250), .CPN(Clk_r), .Q(\Queue[2][22] ) );
  FDN1QM1P \Queue_reg[4][21]  ( .D(n347), .CPN(Clk_r), .Q(\Queue[4][21] ) );
  FDN1QM1P \Queue_reg[3][21]  ( .D(n219), .CPN(Clk_r), .Q(\Queue[3][21] ) );
  FDN1QM1P \Queue_reg[2][21]  ( .D(n251), .CPN(Clk_r), .Q(\Queue[2][21] ) );
  FDN1QM1P \Queue_reg[4][20]  ( .D(n348), .CPN(Clk_r), .Q(\Queue[4][20] ) );
  FDN1QM1P \Queue_reg[3][20]  ( .D(n220), .CPN(Clk_r), .Q(\Queue[3][20] ) );
  FDN1QM1P \Queue_reg[2][20]  ( .D(n252), .CPN(Clk_r), .Q(\Queue[2][20] ) );
  FDN1QM1P \Queue_reg[4][19]  ( .D(n349), .CPN(Clk_r), .Q(\Queue[4][19] ) );
  FDN1QM1P \Queue_reg[3][19]  ( .D(n221), .CPN(Clk_r), .Q(\Queue[3][19] ) );
  FDN1QM1P \Queue_reg[2][19]  ( .D(n253), .CPN(Clk_r), .Q(\Queue[2][19] ) );
  FDN1QM1P \Queue_reg[4][18]  ( .D(n350), .CPN(Clk_r), .Q(\Queue[4][18] ) );
  FDN1QM1P \Queue_reg[3][18]  ( .D(n222), .CPN(Clk_r), .Q(\Queue[3][18] ) );
  FDN1QM1P \Queue_reg[2][18]  ( .D(n254), .CPN(Clk_r), .Q(\Queue[2][18] ) );
  FDN1QM1P \Queue_reg[4][17]  ( .D(n351), .CPN(Clk_r), .Q(\Queue[4][17] ) );
  FDN1QM1P \Queue_reg[3][17]  ( .D(n223), .CPN(Clk_r), .Q(\Queue[3][17] ) );
  FDN1QM1P \Queue_reg[2][17]  ( .D(n255), .CPN(Clk_r), .Q(\Queue[2][17] ) );
  FDN1QM1P \Queue_reg[4][16]  ( .D(n352), .CPN(Clk_r), .Q(\Queue[4][16] ) );
  FDN1QM1P \Queue_reg[3][16]  ( .D(n224), .CPN(Clk_r), .Q(\Queue[3][16] ) );
  FDN1QM1P \Queue_reg[2][16]  ( .D(n256), .CPN(Clk_r), .Q(\Queue[2][16] ) );
  FDN1QM1P \Queue_reg[4][15]  ( .D(n353), .CPN(Clk_r), .Q(\Queue[4][15] ) );
  FDN1QM1P \Queue_reg[3][15]  ( .D(n225), .CPN(Clk_r), .Q(\Queue[3][15] ) );
  FDN1QM1P \Queue_reg[2][15]  ( .D(n257), .CPN(Clk_r), .Q(\Queue[2][15] ) );
  FDN1QM1P \Queue_reg[4][14]  ( .D(n354), .CPN(Clk_r), .Q(\Queue[4][14] ) );
  FDN1QM1P \Queue_reg[3][14]  ( .D(n226), .CPN(Clk_r), .Q(\Queue[3][14] ) );
  FDN1QM1P \Queue_reg[2][14]  ( .D(n258), .CPN(Clk_r), .Q(\Queue[2][14] ) );
  FDN1QM1P \Queue_reg[4][13]  ( .D(n355), .CPN(Clk_r), .Q(\Queue[4][13] ) );
  FDN1QM1P \Queue_reg[3][13]  ( .D(n227), .CPN(Clk_r), .Q(\Queue[3][13] ) );
  FDN1QM1P \Queue_reg[2][13]  ( .D(n259), .CPN(Clk_r), .Q(\Queue[2][13] ) );
  FDN1QM1P \Queue_reg[4][12]  ( .D(n356), .CPN(Clk_r), .Q(\Queue[4][12] ) );
  FDN1QM1P \Queue_reg[3][12]  ( .D(n228), .CPN(Clk_r), .Q(\Queue[3][12] ) );
  FDN1QM1P \Queue_reg[2][12]  ( .D(n260), .CPN(Clk_r), .Q(\Queue[2][12] ) );
  FDN1QM1P \Queue_reg[4][11]  ( .D(n357), .CPN(Clk_r), .Q(\Queue[4][11] ) );
  FDN1QM1P \Queue_reg[3][11]  ( .D(n229), .CPN(Clk_r), .Q(\Queue[3][11] ) );
  FDN1QM1P \Queue_reg[2][11]  ( .D(n261), .CPN(Clk_r), .Q(\Queue[2][11] ) );
  FDN1QM1P \Queue_reg[4][10]  ( .D(n358), .CPN(Clk_r), .Q(\Queue[4][10] ) );
  FDN1QM1P \Queue_reg[3][10]  ( .D(n230), .CPN(Clk_r), .Q(\Queue[3][10] ) );
  FDN1QM1P \Queue_reg[2][10]  ( .D(n262), .CPN(Clk_r), .Q(\Queue[2][10] ) );
  FDN1QM1P \Queue_reg[4][9]  ( .D(n359), .CPN(Clk_r), .Q(\Queue[4][9] ) );
  FDN1QM1P \Queue_reg[3][9]  ( .D(n231), .CPN(Clk_r), .Q(\Queue[3][9] ) );
  FDN1QM1P \Queue_reg[2][9]  ( .D(n263), .CPN(Clk_r), .Q(\Queue[2][9] ) );
  FDN1QM1P \Queue_reg[4][8]  ( .D(n360), .CPN(Clk_r), .Q(\Queue[4][8] ) );
  FDN1QM1P \Queue_reg[3][8]  ( .D(n232), .CPN(Clk_r), .Q(\Queue[3][8] ) );
  FDN1QM1P \Queue_reg[2][8]  ( .D(n264), .CPN(Clk_r), .Q(\Queue[2][8] ) );
  FDN1QM1P \Queue_reg[4][7]  ( .D(n361), .CPN(Clk_r), .Q(\Queue[4][7] ) );
  FDN1QM1P \Queue_reg[3][7]  ( .D(n233), .CPN(Clk_r), .Q(\Queue[3][7] ) );
  FDN1QM1P \Queue_reg[2][7]  ( .D(n265), .CPN(Clk_r), .Q(\Queue[2][7] ) );
  FDN1QM1P \Queue_reg[4][6]  ( .D(n362), .CPN(Clk_r), .Q(\Queue[4][6] ) );
  FDN1QM1P \Queue_reg[3][6]  ( .D(n234), .CPN(Clk_r), .Q(\Queue[3][6] ) );
  FDN1QM1P \Queue_reg[2][6]  ( .D(n266), .CPN(Clk_r), .Q(\Queue[2][6] ) );
  FDN1QM1P \Queue_reg[4][5]  ( .D(n363), .CPN(Clk_r), .Q(\Queue[4][5] ) );
  FDN1QM1P \Queue_reg[3][5]  ( .D(n235), .CPN(Clk_r), .Q(\Queue[3][5] ) );
  FDN1QM1P \Queue_reg[2][5]  ( .D(n267), .CPN(Clk_r), .Q(\Queue[2][5] ) );
  FDN1QM1P \Queue_reg[4][4]  ( .D(n364), .CPN(Clk_r), .Q(\Queue[4][4] ) );
  FDN1QM1P \Queue_reg[3][4]  ( .D(n236), .CPN(Clk_r), .Q(\Queue[3][4] ) );
  FDN1QM1P \Queue_reg[2][4]  ( .D(n268), .CPN(Clk_r), .Q(\Queue[2][4] ) );
  FDN1QM1P \Queue_reg[4][3]  ( .D(n365), .CPN(Clk_r), .Q(\Queue[4][3] ) );
  FDN1QM1P \Queue_reg[3][3]  ( .D(n237), .CPN(Clk_r), .Q(\Queue[3][3] ) );
  FDN1QM1P \Queue_reg[2][3]  ( .D(n269), .CPN(Clk_r), .Q(\Queue[2][3] ) );
  FDN1QM1P \Queue_reg[4][2]  ( .D(n366), .CPN(Clk_r), .Q(\Queue[4][2] ) );
  FDN1QM1P \Queue_reg[3][2]  ( .D(n238), .CPN(Clk_r), .Q(\Queue[3][2] ) );
  FDN1QM1P \Queue_reg[2][2]  ( .D(n270), .CPN(Clk_r), .Q(\Queue[2][2] ) );
  FDN1QM1P \Queue_reg[4][1]  ( .D(n367), .CPN(Clk_r), .Q(\Queue[4][1] ) );
  FDN1QM1P \Queue_reg[3][1]  ( .D(n239), .CPN(Clk_r), .Q(\Queue[3][1] ) );
  FDN1QM1P \Queue_reg[2][1]  ( .D(n271), .CPN(Clk_r), .Q(\Queue[2][1] ) );
  FDN1QM1P \Queue_reg[4][0]  ( .D(n368), .CPN(Clk_r), .Q(\Queue[4][0] ) );
  FDN1QM1P \Queue_reg[3][0]  ( .D(n240), .CPN(Clk_r), .Q(\Queue[3][0] ) );
  FDN1QM1P \Queue_reg[2][0]  ( .D(n272), .CPN(Clk_r), .Q(\Queue[2][0] ) );
  FDN1QM1P \Queue_reg[4][31]  ( .D(n369), .CPN(Clk_r), .Q(\Queue[4][31] ) );
  FDN1QM1P \Queue_reg[3][31]  ( .D(n209), .CPN(Clk_r), .Q(\Queue[3][31] ) );
  FDN1QM1P \Queue_reg[2][31]  ( .D(n241), .CPN(Clk_r), .Q(\Queue[2][31] ) );
  FDN2QM1P Full_reg ( .D(n337), .CPN(Clk_r), .CD(Rst_n), .Q(Full) );
  FDN1QM1P \Queue_reg[1][31]  ( .D(n305), .CPN(Clk_r), .Q(\Queue[1][31] ) );
  FDN1QM1P \Queue_reg[0][31]  ( .D(n273), .CPN(Clk_r), .Q(Packet_Out[31]) );
  FDN1QM1P \Queue_reg[1][30]  ( .D(n306), .CPN(Clk_r), .Q(\Queue[1][30] ) );
  FDN1QM1P \Queue_reg[0][30]  ( .D(n274), .CPN(Clk_r), .Q(Packet_Out[30]) );
  FDN1QM1P \Queue_reg[1][29]  ( .D(n307), .CPN(Clk_r), .Q(\Queue[1][29] ) );
  FDN1QM1P \Queue_reg[0][29]  ( .D(n275), .CPN(Clk_r), .Q(Packet_Out[29]) );
  FDN1QM1P \Queue_reg[1][28]  ( .D(n308), .CPN(Clk_r), .Q(\Queue[1][28] ) );
  FDN1QM1P \Queue_reg[0][28]  ( .D(n276), .CPN(Clk_r), .Q(Packet_Out[28]) );
  FDN1QM1P \Queue_reg[1][27]  ( .D(n309), .CPN(Clk_r), .Q(\Queue[1][27] ) );
  FDN1QM1P \Queue_reg[0][27]  ( .D(n277), .CPN(Clk_r), .Q(Packet_Out[27]) );
  FDN1QM1P \Queue_reg[1][26]  ( .D(n310), .CPN(Clk_r), .Q(\Queue[1][26] ) );
  FDN1QM1P \Queue_reg[0][26]  ( .D(n278), .CPN(Clk_r), .Q(Packet_Out[26]) );
  FDN1QM1P \Queue_reg[1][25]  ( .D(n311), .CPN(Clk_r), .Q(\Queue[1][25] ) );
  FDN1QM1P \Queue_reg[0][25]  ( .D(n279), .CPN(Clk_r), .Q(Packet_Out[25]) );
  FDN1QM1P \Queue_reg[1][24]  ( .D(n312), .CPN(Clk_r), .Q(\Queue[1][24] ) );
  FDN1QM1P \Queue_reg[0][24]  ( .D(n280), .CPN(Clk_r), .Q(Packet_Out[24]) );
  FDN1QM1P \Queue_reg[1][23]  ( .D(n313), .CPN(Clk_r), .Q(\Queue[1][23] ) );
  FDN1QM1P \Queue_reg[0][23]  ( .D(n281), .CPN(Clk_r), .Q(Packet_Out[23]) );
  FDN1QM1P \Queue_reg[1][22]  ( .D(n314), .CPN(Clk_r), .Q(\Queue[1][22] ) );
  FDN1QM1P \Queue_reg[0][22]  ( .D(n282), .CPN(Clk_r), .Q(Packet_Out[22]) );
  FDN1QM1P \Queue_reg[1][21]  ( .D(n315), .CPN(Clk_r), .Q(\Queue[1][21] ) );
  FDN1QM1P \Queue_reg[0][21]  ( .D(n283), .CPN(Clk_r), .Q(Packet_Out[21]) );
  FDN1QM1P \Queue_reg[1][20]  ( .D(n316), .CPN(Clk_r), .Q(\Queue[1][20] ) );
  FDN1QM1P \Queue_reg[0][20]  ( .D(n284), .CPN(Clk_r), .Q(Packet_Out[20]) );
  FDN1QM1P \Queue_reg[1][19]  ( .D(n317), .CPN(Clk_r), .Q(\Queue[1][19] ) );
  FDN1QM1P \Queue_reg[0][19]  ( .D(n285), .CPN(Clk_r), .Q(Packet_Out[19]) );
  FDN1QM1P \Queue_reg[1][18]  ( .D(n318), .CPN(Clk_r), .Q(\Queue[1][18] ) );
  FDN1QM1P \Queue_reg[0][18]  ( .D(n286), .CPN(Clk_r), .Q(Packet_Out[18]) );
  FDN1QM1P \Queue_reg[1][17]  ( .D(n319), .CPN(Clk_r), .Q(\Queue[1][17] ) );
  FDN1QM1P \Queue_reg[0][17]  ( .D(n287), .CPN(Clk_r), .Q(Packet_Out[17]) );
  FDN1QM1P \Queue_reg[1][16]  ( .D(n320), .CPN(Clk_r), .Q(\Queue[1][16] ) );
  FDN1QM1P \Queue_reg[0][16]  ( .D(n288), .CPN(Clk_r), .Q(Packet_Out[16]) );
  FDN1QM1P \Queue_reg[1][15]  ( .D(n321), .CPN(Clk_r), .Q(\Queue[1][15] ) );
  FDN1QM1P \Queue_reg[0][15]  ( .D(n289), .CPN(Clk_r), .Q(Packet_Out[15]) );
  FDN1QM1P \Queue_reg[1][14]  ( .D(n322), .CPN(Clk_r), .Q(\Queue[1][14] ) );
  FDN1QM1P \Queue_reg[0][14]  ( .D(n290), .CPN(Clk_r), .Q(Packet_Out[14]) );
  FDN1QM1P \Queue_reg[1][13]  ( .D(n323), .CPN(Clk_r), .Q(\Queue[1][13] ) );
  FDN1QM1P \Queue_reg[0][13]  ( .D(n291), .CPN(Clk_r), .Q(Packet_Out[13]) );
  FDN1QM1P \Queue_reg[1][12]  ( .D(n324), .CPN(Clk_r), .Q(\Queue[1][12] ) );
  FDN1QM1P \Queue_reg[0][12]  ( .D(n292), .CPN(Clk_r), .Q(Packet_Out[12]) );
  FDN1QM1P \Queue_reg[1][11]  ( .D(n325), .CPN(Clk_r), .Q(\Queue[1][11] ) );
  FDN1QM1P \Queue_reg[0][11]  ( .D(n293), .CPN(Clk_r), .Q(Packet_Out[11]) );
  FDN1QM1P \Queue_reg[1][10]  ( .D(n326), .CPN(Clk_r), .Q(\Queue[1][10] ) );
  FDN1QM1P \Queue_reg[0][10]  ( .D(n294), .CPN(Clk_r), .Q(Packet_Out[10]) );
  FDN1QM1P \Queue_reg[1][9]  ( .D(n327), .CPN(Clk_r), .Q(\Queue[1][9] ) );
  FDN1QM1P \Queue_reg[0][9]  ( .D(n295), .CPN(Clk_r), .Q(Packet_Out[9]) );
  FDN1QM1P \Queue_reg[1][8]  ( .D(n328), .CPN(Clk_r), .Q(\Queue[1][8] ) );
  FDN1QM1P \Queue_reg[0][8]  ( .D(n296), .CPN(Clk_r), .Q(Packet_Out[8]) );
  FDN1QM1P \Queue_reg[1][7]  ( .D(n329), .CPN(Clk_r), .Q(\Queue[1][7] ) );
  FDN1QM1P \Queue_reg[0][7]  ( .D(n297), .CPN(Clk_r), .Q(Packet_Out[7]) );
  FDN1QM1P \Queue_reg[1][6]  ( .D(n330), .CPN(Clk_r), .Q(\Queue[1][6] ) );
  FDN1QM1P \Queue_reg[0][6]  ( .D(n298), .CPN(Clk_r), .Q(Packet_Out[6]) );
  FDN1QM1P \Queue_reg[1][5]  ( .D(n331), .CPN(Clk_r), .Q(\Queue[1][5] ) );
  FDN1QM1P \Queue_reg[0][5]  ( .D(n299), .CPN(Clk_r), .Q(Packet_Out[5]) );
  FDN1QM1P \Queue_reg[1][4]  ( .D(n332), .CPN(Clk_r), .Q(\Queue[1][4] ) );
  FDN1QM1P \Queue_reg[0][4]  ( .D(n300), .CPN(Clk_r), .Q(Packet_Out[4]) );
  FDN1QM1P \Queue_reg[1][3]  ( .D(n333), .CPN(Clk_r), .Q(\Queue[1][3] ) );
  FDN1QM1P \Queue_reg[0][3]  ( .D(n301), .CPN(Clk_r), .Q(Packet_Out[3]) );
  FDN1QM1P \Queue_reg[1][2]  ( .D(n334), .CPN(Clk_r), .Q(\Queue[1][2] ) );
  FDN1QM1P \Queue_reg[0][2]  ( .D(n302), .CPN(Clk_r), .Q(Packet_Out[2]) );
  FDN1QM1P \Queue_reg[1][1]  ( .D(n335), .CPN(Clk_r), .Q(\Queue[1][1] ) );
  FDN1QM1P \Queue_reg[0][1]  ( .D(n303), .CPN(Clk_r), .Q(Packet_Out[1]) );
  FDN1QM1P \Queue_reg[1][0]  ( .D(n336), .CPN(Clk_r), .Q(\Queue[1][0] ) );
  FDN1QM1P \Queue_reg[0][0]  ( .D(n304), .CPN(Clk_r), .Q(Packet_Out[0]) );
  FDN2QM3P Empty_reg ( .D(n1), .CPN(Clk_r), .CD(Rst_n), .Q(n2) );
  N1M3P U3 ( .A(n2), .Z(Empty) );
  BUFM1P U4 ( .A(Empty), .Z(n3) );
  BUFM1P U5 ( .A(n56), .Z(n5) );
  BUFM1P U6 ( .A(n97), .Z(n8) );
  AOI21M3P U7 ( .A(Write_Ack), .B(n100), .C(n141), .Z(n57) );
  OAI211M4P U8 ( .A(n100), .B(n137), .C(Write_Ack), .D(n24), .Z(n59) );
  BUFM1P U9 ( .A(n135), .Z(n10) );
  BUFM1P U10 ( .A(n208), .Z(n12) );
  AOI21M3P U11 ( .A(Write_Ack), .B(n140), .C(n141), .Z(n132) );
  BUFM2P U12 ( .A(n205), .Z(n4) );
  NR3M1P U13 ( .A(Count[2]), .B(n23), .C(n22), .Z(n140) );
  N1M1P U14 ( .A(n18), .Z(n139) );
  NR2FM3P U15 ( .A(n61), .B(n21), .Z(n20) );
  AOI22M4P U16 ( .A(n3), .B(n374), .C(n375), .D(n376), .Z(n1) );
  BUFM2P U17 ( .A(n56), .Z(n6) );
  BUFM2P U18 ( .A(n208), .Z(n11) );
  BUFM2P U19 ( .A(n135), .Z(n9) );
  BUFM2P U20 ( .A(n97), .Z(n7) );
  OAI211M4P U21 ( .A(n140), .B(n139), .C(Rst_n), .D(n138), .Z(n206) );
  NR3M1P U22 ( .A(n23), .B(Count[2]), .C(Count[1]), .Z(n376) );
  NR3M1P U23 ( .A(Count[0]), .B(Count[1]), .C(n19), .Z(n142) );
  NR3M1P U24 ( .A(Count[0]), .B(Count[2]), .C(n22), .Z(n100) );
  N1M1P U25 ( .A(Count[0]), .Z(n23) );
  N1M1P U26 ( .A(Count[1]), .Z(n22) );
  NR2M2P U27 ( .A(Read_Ack), .B(n99), .Z(n18) );
  ND2M1P U28 ( .A(Read_Ack), .B(Rst_n), .Z(n141) );
  N1M1P U29 ( .A(Read_Ack), .Z(n137) );
  N1M1P U30 ( .A(Count[2]), .Z(n19) );
  N1M1P U31 ( .A(n142), .Z(n21) );
  OAI211M4P U32 ( .A(n100), .B(n139), .C(Rst_n), .D(n138), .Z(n133) );
  ND2M1P U33 ( .A(n18), .B(Rst_n), .Z(n61) );
  N1M1P U34 ( .A(Write_Ack), .Z(n99) );
  N1M1P U35 ( .A(Packet_In[31]), .Z(n373) );
  N1M1P U36 ( .A(Packet_In[25]), .Z(n194) );
  N1M1P U37 ( .A(Packet_In[27]), .Z(n198) );
  N1M1P U38 ( .A(Packet_In[28]), .Z(n200) );
  N1M1P U39 ( .A(Packet_In[26]), .Z(n196) );
  N1M1P U40 ( .A(Packet_In[21]), .Z(n186) );
  N1M1P U41 ( .A(Packet_In[23]), .Z(n190) );
  N1M1P U42 ( .A(Packet_In[20]), .Z(n184) );
  N1M1P U43 ( .A(Packet_In[29]), .Z(n202) );
  N1M1P U44 ( .A(Packet_In[22]), .Z(n188) );
  N1M1P U45 ( .A(Packet_In[24]), .Z(n192) );
  N1M1P U46 ( .A(Packet_In[12]), .Z(n168) );
  N1M1P U47 ( .A(Packet_In[6]), .Z(n156) );
  N1M1P U48 ( .A(Packet_In[1]), .Z(n146) );
  N1M1P U49 ( .A(Packet_In[4]), .Z(n152) );
  N1M1P U50 ( .A(Packet_In[11]), .Z(n166) );
  N1M1P U51 ( .A(Packet_In[16]), .Z(n176) );
  N1M1P U52 ( .A(Packet_In[2]), .Z(n148) );
  N1M1P U53 ( .A(Packet_In[3]), .Z(n150) );
  N1M1P U54 ( .A(Packet_In[15]), .Z(n174) );
  N1M1P U55 ( .A(Packet_In[14]), .Z(n172) );
  N1M1P U56 ( .A(Packet_In[17]), .Z(n178) );
  N1M1P U57 ( .A(Packet_In[18]), .Z(n180) );
  N1M1P U58 ( .A(Packet_In[30]), .Z(n204) );
  N1M1P U59 ( .A(Packet_In[5]), .Z(n154) );
  N1M1P U60 ( .A(Packet_In[19]), .Z(n182) );
  N1M1P U61 ( .A(Packet_In[7]), .Z(n158) );
  N1M1P U62 ( .A(Packet_In[8]), .Z(n160) );
  N1M1P U63 ( .A(Packet_In[0]), .Z(n144) );
  N1M1P U64 ( .A(Packet_In[10]), .Z(n164) );
  N1M1P U65 ( .A(Packet_In[9]), .Z(n162) );
  N1M1P U66 ( .A(Packet_In[13]), .Z(n170) );
  N1M2P U67 ( .A(n62), .Z(n94) );
  AOI21M6P U68 ( .A(Write_Ack), .B(n376), .C(n141), .Z(n95) );
  OAI211M1P U69 ( .A(n142), .B(n137), .C(Write_Ack), .D(n136), .Z(n208) );
  OAI211M1P U70 ( .A(n376), .B(n137), .C(Write_Ack), .D(n62), .Z(n97) );
  OAI211M1P U71 ( .A(n140), .B(n137), .C(Write_Ack), .D(n98), .Z(n135) );
  NR2M1P U72 ( .A(n137), .B(Write_Ack), .Z(n375) );
  NR2M1P U73 ( .A(n18), .B(n375), .Z(n374) );
  AOI22CDNM1P U74 ( .A(n374), .B(n23), .C(n374), .D(n23), .Z(n372) );
  N1M1P U75 ( .A(n375), .Z(n17) );
  AOI22M1P U76 ( .A(Count[0]), .B(n139), .C(n17), .D(n23), .Z(n14) );
  AOI22CDNM1P U77 ( .A(Count[1]), .B(n14), .C(n14), .D(Count[1]), .Z(n371) );
  ND3M1P U78 ( .A(n23), .B(n19), .C(n22), .Z(n60) );
  ND2M1P U79 ( .A(Count[1]), .B(Read_Ack), .Z(n13) );
  OAI211M1P U80 ( .A(Count[1]), .B(n139), .C(n14), .D(n13), .Z(n15) );
  AOI22M1P U81 ( .A(n18), .B(n140), .C(Count[2]), .D(n15), .Z(n16) );
  OAI21M1P U82 ( .A(n60), .B(n17), .C(n16), .Z(n370) );
  AOI22CDNM1P U83 ( .A(n20), .B(n373), .C(\Queue[4][31] ), .D(n20), .Z(n369)
         );
  AOI22CDNM1P U84 ( .A(n20), .B(n144), .C(\Queue[4][0] ), .D(n20), .Z(n368) );
  AOI22CDNM1P U85 ( .A(n20), .B(n146), .C(\Queue[4][1] ), .D(n20), .Z(n367) );
  AOI22CDNM1P U86 ( .A(n20), .B(n148), .C(\Queue[4][2] ), .D(n20), .Z(n366) );
  AOI22CDNM1P U87 ( .A(n20), .B(n150), .C(\Queue[4][3] ), .D(n20), .Z(n365) );
  AOI22CDNM1P U88 ( .A(n20), .B(n152), .C(\Queue[4][4] ), .D(n20), .Z(n364) );
  AOI22CDNM1P U89 ( .A(n20), .B(n154), .C(\Queue[4][5] ), .D(n20), .Z(n363) );
  AOI22CDNM1P U90 ( .A(n20), .B(n156), .C(\Queue[4][6] ), .D(n20), .Z(n362) );
  AOI22CDNM1P U91 ( .A(n20), .B(n158), .C(\Queue[4][7] ), .D(n20), .Z(n361) );
  AOI22CDNM1P U92 ( .A(n20), .B(n160), .C(\Queue[4][8] ), .D(n20), .Z(n360) );
  AOI22CDNM1P U93 ( .A(n20), .B(n162), .C(\Queue[4][9] ), .D(n20), .Z(n359) );
  AOI22CDNM1P U94 ( .A(n20), .B(n164), .C(\Queue[4][10] ), .D(n20), .Z(n358)
         );
  AOI22CDNM1P U95 ( .A(n20), .B(n166), .C(\Queue[4][11] ), .D(n20), .Z(n357)
         );
  AOI22CDNM1P U96 ( .A(n20), .B(n168), .C(\Queue[4][12] ), .D(n20), .Z(n356)
         );
  AOI22CDNM1P U97 ( .A(n20), .B(n170), .C(\Queue[4][13] ), .D(n20), .Z(n355)
         );
  AOI22CDNM1P U98 ( .A(n20), .B(n172), .C(\Queue[4][14] ), .D(n20), .Z(n354)
         );
  AOI22CDNM1P U99 ( .A(n20), .B(n174), .C(\Queue[4][15] ), .D(n20), .Z(n353)
         );
  AOI22CDNM1P U100 ( .A(n20), .B(n176), .C(\Queue[4][16] ), .D(n20), .Z(n352)
         );
  AOI22CDNM1P U101 ( .A(n20), .B(n178), .C(\Queue[4][17] ), .D(n20), .Z(n351)
         );
  AOI22CDNM1P U102 ( .A(n20), .B(n180), .C(\Queue[4][18] ), .D(n20), .Z(n350)
         );
  AOI22CDNM1P U103 ( .A(n20), .B(n182), .C(\Queue[4][19] ), .D(n20), .Z(n349)
         );
  AOI22CDNM1P U104 ( .A(n20), .B(n184), .C(\Queue[4][20] ), .D(n20), .Z(n348)
         );
  AOI22CDNM1P U105 ( .A(n20), .B(n186), .C(\Queue[4][21] ), .D(n20), .Z(n347)
         );
  AOI22CDNM1P U106 ( .A(n20), .B(n188), .C(\Queue[4][22] ), .D(n20), .Z(n346)
         );
  AOI22CDNM1P U107 ( .A(n20), .B(n190), .C(\Queue[4][23] ), .D(n20), .Z(n345)
         );
  AOI22CDNM1P U108 ( .A(n20), .B(n192), .C(\Queue[4][24] ), .D(n20), .Z(n344)
         );
  AOI22CDNM1P U109 ( .A(n20), .B(n194), .C(\Queue[4][25] ), .D(n20), .Z(n343)
         );
  AOI22CDNM1P U110 ( .A(n20), .B(n196), .C(\Queue[4][26] ), .D(n20), .Z(n342)
         );
  AOI22CDNM1P U111 ( .A(n20), .B(n198), .C(\Queue[4][27] ), .D(n20), .Z(n341)
         );
  AOI22CDNM1P U112 ( .A(n20), .B(n200), .C(\Queue[4][28] ), .D(n20), .Z(n340)
         );
  AOI22CDNM1P U113 ( .A(n20), .B(n202), .C(\Queue[4][29] ), .D(n20), .Z(n339)
         );
  AOI22CDNM1P U114 ( .A(n20), .B(n204), .C(\Queue[4][30] ), .D(n20), .Z(n338)
         );
  OAI22CDNM1P U115 ( .A(n139), .B(n21), .C(n374), .D(Full), .Z(n337) );
  OA21M1P U116 ( .A(n139), .B(n376), .C(Rst_n), .Z(n24) );
  OAI21M1P U117 ( .A(Write_Ack), .B(Read_Ack), .C(n24), .Z(n56) );
  AOI22M1P U118 ( .A(\Queue[2][0] ), .B(n57), .C(\Queue[1][0] ), .D(n5), .Z(
        n25) );
  OAI21M1P U119 ( .A(n144), .B(n59), .C(n25), .Z(n336) );
  AOI22M1P U120 ( .A(n57), .B(\Queue[2][1] ), .C(\Queue[1][1] ), .D(n5), .Z(
        n26) );
  OAI21M1P U121 ( .A(n59), .B(n146), .C(n26), .Z(n335) );
  AOI22M1P U122 ( .A(n57), .B(\Queue[2][2] ), .C(\Queue[1][2] ), .D(n5), .Z(
        n27) );
  OAI21M1P U123 ( .A(n59), .B(n148), .C(n27), .Z(n334) );
  AOI22M1P U124 ( .A(n57), .B(\Queue[2][3] ), .C(\Queue[1][3] ), .D(n5), .Z(
        n28) );
  OAI21M1P U125 ( .A(n59), .B(n150), .C(n28), .Z(n333) );
  AOI22M1P U126 ( .A(n57), .B(\Queue[2][4] ), .C(\Queue[1][4] ), .D(n5), .Z(
        n29) );
  OAI21M1P U127 ( .A(n59), .B(n152), .C(n29), .Z(n332) );
  AOI22M1P U128 ( .A(n57), .B(\Queue[2][5] ), .C(\Queue[1][5] ), .D(n5), .Z(
        n30) );
  OAI21M1P U129 ( .A(n59), .B(n154), .C(n30), .Z(n331) );
  AOI22M1P U130 ( .A(n57), .B(\Queue[2][6] ), .C(\Queue[1][6] ), .D(n5), .Z(
        n31) );
  OAI21M1P U131 ( .A(n59), .B(n156), .C(n31), .Z(n330) );
  AOI22M1P U132 ( .A(n57), .B(\Queue[2][7] ), .C(\Queue[1][7] ), .D(n5), .Z(
        n32) );
  OAI21M1P U133 ( .A(n59), .B(n158), .C(n32), .Z(n329) );
  AOI22M1P U134 ( .A(n57), .B(\Queue[2][8] ), .C(\Queue[1][8] ), .D(n5), .Z(
        n33) );
  OAI21M1P U135 ( .A(n59), .B(n160), .C(n33), .Z(n328) );
  AOI22M1P U136 ( .A(n57), .B(\Queue[2][9] ), .C(\Queue[1][9] ), .D(n6), .Z(
        n34) );
  OAI21M1P U137 ( .A(n59), .B(n162), .C(n34), .Z(n327) );
  AOI22M1P U138 ( .A(n57), .B(\Queue[2][10] ), .C(\Queue[1][10] ), .D(n6), .Z(
        n35) );
  OAI21M1P U139 ( .A(n59), .B(n164), .C(n35), .Z(n326) );
  AOI22M1P U140 ( .A(n57), .B(\Queue[2][11] ), .C(\Queue[1][11] ), .D(n6), .Z(
        n36) );
  OAI21M1P U141 ( .A(n59), .B(n166), .C(n36), .Z(n325) );
  AOI22M1P U142 ( .A(n57), .B(\Queue[2][12] ), .C(\Queue[1][12] ), .D(n6), .Z(
        n37) );
  OAI21M1P U143 ( .A(n59), .B(n168), .C(n37), .Z(n324) );
  AOI22M1P U144 ( .A(n57), .B(\Queue[2][13] ), .C(\Queue[1][13] ), .D(n6), .Z(
        n38) );
  OAI21M1P U145 ( .A(n59), .B(n170), .C(n38), .Z(n323) );
  AOI22M1P U146 ( .A(n57), .B(\Queue[2][14] ), .C(\Queue[1][14] ), .D(n6), .Z(
        n39) );
  OAI21M1P U147 ( .A(n59), .B(n172), .C(n39), .Z(n322) );
  AOI22M1P U148 ( .A(n57), .B(\Queue[2][15] ), .C(\Queue[1][15] ), .D(n6), .Z(
        n40) );
  OAI21M1P U149 ( .A(n59), .B(n174), .C(n40), .Z(n321) );
  AOI22M1P U150 ( .A(n57), .B(\Queue[2][16] ), .C(\Queue[1][16] ), .D(n6), .Z(
        n41) );
  OAI21M1P U151 ( .A(n59), .B(n176), .C(n41), .Z(n320) );
  AOI22M1P U152 ( .A(n57), .B(\Queue[2][17] ), .C(\Queue[1][17] ), .D(n6), .Z(
        n42) );
  OAI21M1P U153 ( .A(n59), .B(n178), .C(n42), .Z(n319) );
  AOI22M1P U154 ( .A(n57), .B(\Queue[2][18] ), .C(\Queue[1][18] ), .D(n6), .Z(
        n43) );
  OAI21M1P U155 ( .A(n59), .B(n180), .C(n43), .Z(n318) );
  AOI22M1P U156 ( .A(n57), .B(\Queue[2][19] ), .C(\Queue[1][19] ), .D(n6), .Z(
        n44) );
  OAI21M1P U157 ( .A(n59), .B(n182), .C(n44), .Z(n317) );
  AOI22M1P U158 ( .A(n57), .B(\Queue[2][20] ), .C(\Queue[1][20] ), .D(n6), .Z(
        n45) );
  OAI21M1P U159 ( .A(n59), .B(n184), .C(n45), .Z(n316) );
  AOI22M1P U160 ( .A(n57), .B(\Queue[2][21] ), .C(\Queue[1][21] ), .D(n6), .Z(
        n46) );
  OAI21M1P U161 ( .A(n59), .B(n186), .C(n46), .Z(n315) );
  AOI22M1P U162 ( .A(n57), .B(\Queue[2][22] ), .C(\Queue[1][22] ), .D(n6), .Z(
        n47) );
  OAI21M1P U163 ( .A(n59), .B(n188), .C(n47), .Z(n314) );
  AOI22M1P U164 ( .A(n57), .B(\Queue[2][23] ), .C(\Queue[1][23] ), .D(n6), .Z(
        n48) );
  OAI21M1P U165 ( .A(n59), .B(n190), .C(n48), .Z(n313) );
  AOI22M1P U166 ( .A(n57), .B(\Queue[2][24] ), .C(\Queue[1][24] ), .D(n6), .Z(
        n49) );
  OAI21M1P U167 ( .A(n59), .B(n192), .C(n49), .Z(n312) );
  AOI22M1P U168 ( .A(n57), .B(\Queue[2][25] ), .C(\Queue[1][25] ), .D(n6), .Z(
        n50) );
  OAI21M1P U169 ( .A(n59), .B(n194), .C(n50), .Z(n311) );
  AOI22M1P U170 ( .A(n57), .B(\Queue[2][26] ), .C(\Queue[1][26] ), .D(n6), .Z(
        n51) );
  OAI21M1P U171 ( .A(n59), .B(n196), .C(n51), .Z(n310) );
  AOI22M1P U172 ( .A(n57), .B(\Queue[2][27] ), .C(\Queue[1][27] ), .D(n6), .Z(
        n52) );
  OAI21M1P U173 ( .A(n59), .B(n198), .C(n52), .Z(n309) );
  AOI22M1P U174 ( .A(n57), .B(\Queue[2][28] ), .C(\Queue[1][28] ), .D(n6), .Z(
        n53) );
  OAI21M1P U175 ( .A(n59), .B(n200), .C(n53), .Z(n308) );
  AOI22M1P U176 ( .A(n57), .B(\Queue[2][29] ), .C(\Queue[1][29] ), .D(n5), .Z(
        n54) );
  OAI21M1P U177 ( .A(n59), .B(n202), .C(n54), .Z(n307) );
  AOI22M1P U178 ( .A(n57), .B(\Queue[2][30] ), .C(\Queue[1][30] ), .D(n6), .Z(
        n55) );
  OAI21M1P U179 ( .A(n59), .B(n204), .C(n55), .Z(n306) );
  AOI22M1P U180 ( .A(n57), .B(\Queue[2][31] ), .C(\Queue[1][31] ), .D(n6), .Z(
        n58) );
  OAI21M1P U181 ( .A(n59), .B(n373), .C(n58), .Z(n305) );
  OAI21M1P U182 ( .A(n61), .B(n60), .C(n141), .Z(n62) );
  AOI22M1P U183 ( .A(\Queue[1][0] ), .B(n95), .C(n94), .D(Packet_Out[0]), .Z(
        n63) );
  OAI21M1P U184 ( .A(n144), .B(n7), .C(n63), .Z(n304) );
  AOI22M1P U185 ( .A(\Queue[1][1] ), .B(n95), .C(n94), .D(Packet_Out[1]), .Z(
        n64) );
  OAI21M1P U186 ( .A(n146), .B(n7), .C(n64), .Z(n303) );
  AOI22M1P U187 ( .A(\Queue[1][2] ), .B(n95), .C(n94), .D(Packet_Out[2]), .Z(
        n65) );
  OAI21M1P U188 ( .A(n148), .B(n7), .C(n65), .Z(n302) );
  AOI22M1P U189 ( .A(\Queue[1][3] ), .B(n95), .C(n94), .D(Packet_Out[3]), .Z(
        n66) );
  OAI21M1P U190 ( .A(n150), .B(n7), .C(n66), .Z(n301) );
  AOI22M1P U191 ( .A(\Queue[1][4] ), .B(n95), .C(n94), .D(Packet_Out[4]), .Z(
        n67) );
  OAI21M1P U192 ( .A(n152), .B(n7), .C(n67), .Z(n300) );
  AOI22M1P U193 ( .A(\Queue[1][5] ), .B(n95), .C(n94), .D(Packet_Out[5]), .Z(
        n68) );
  OAI21M1P U194 ( .A(n154), .B(n7), .C(n68), .Z(n299) );
  AOI22M1P U195 ( .A(\Queue[1][6] ), .B(n95), .C(n94), .D(Packet_Out[6]), .Z(
        n69) );
  OAI21M1P U196 ( .A(n156), .B(n7), .C(n69), .Z(n298) );
  AOI22M1P U197 ( .A(\Queue[1][7] ), .B(n95), .C(n94), .D(Packet_Out[7]), .Z(
        n70) );
  OAI21M1P U198 ( .A(n158), .B(n7), .C(n70), .Z(n297) );
  AOI22M1P U199 ( .A(\Queue[1][8] ), .B(n95), .C(n94), .D(Packet_Out[8]), .Z(
        n71) );
  OAI21M1P U200 ( .A(n160), .B(n7), .C(n71), .Z(n296) );
  AOI22M1P U201 ( .A(\Queue[1][9] ), .B(n95), .C(n94), .D(Packet_Out[9]), .Z(
        n72) );
  OAI21M1P U202 ( .A(n162), .B(n7), .C(n72), .Z(n295) );
  AOI22M1P U203 ( .A(\Queue[1][10] ), .B(n95), .C(n94), .D(Packet_Out[10]), 
        .Z(n73) );
  OAI21M1P U204 ( .A(n164), .B(n7), .C(n73), .Z(n294) );
  AOI22M1P U205 ( .A(\Queue[1][11] ), .B(n95), .C(n94), .D(Packet_Out[11]), 
        .Z(n74) );
  OAI21M1P U206 ( .A(n166), .B(n7), .C(n74), .Z(n293) );
  AOI22M1P U207 ( .A(\Queue[1][12] ), .B(n95), .C(n94), .D(Packet_Out[12]), 
        .Z(n75) );
  OAI21M1P U208 ( .A(n168), .B(n7), .C(n75), .Z(n292) );
  AOI22M1P U209 ( .A(\Queue[1][13] ), .B(n95), .C(n94), .D(Packet_Out[13]), 
        .Z(n76) );
  OAI21M1P U210 ( .A(n170), .B(n7), .C(n76), .Z(n291) );
  AOI22M1P U211 ( .A(\Queue[1][14] ), .B(n95), .C(n94), .D(Packet_Out[14]), 
        .Z(n77) );
  OAI21M1P U212 ( .A(n172), .B(n7), .C(n77), .Z(n290) );
  AOI22M1P U213 ( .A(\Queue[1][15] ), .B(n95), .C(n94), .D(Packet_Out[15]), 
        .Z(n78) );
  OAI21M1P U214 ( .A(n174), .B(n7), .C(n78), .Z(n289) );
  AOI22M1P U215 ( .A(\Queue[1][16] ), .B(n95), .C(n94), .D(Packet_Out[16]), 
        .Z(n79) );
  OAI21M1P U216 ( .A(n176), .B(n7), .C(n79), .Z(n288) );
  AOI22M1P U217 ( .A(\Queue[1][17] ), .B(n95), .C(n94), .D(Packet_Out[17]), 
        .Z(n80) );
  OAI21M1P U218 ( .A(n178), .B(n7), .C(n80), .Z(n287) );
  AOI22M1P U219 ( .A(\Queue[1][18] ), .B(n95), .C(n94), .D(Packet_Out[18]), 
        .Z(n81) );
  OAI21M1P U220 ( .A(n180), .B(n7), .C(n81), .Z(n286) );
  AOI22M1P U221 ( .A(\Queue[1][19] ), .B(n95), .C(n94), .D(Packet_Out[19]), 
        .Z(n82) );
  OAI21M1P U222 ( .A(n182), .B(n7), .C(n82), .Z(n285) );
  AOI22M1P U223 ( .A(\Queue[1][20] ), .B(n95), .C(n94), .D(Packet_Out[20]), 
        .Z(n83) );
  OAI21M1P U224 ( .A(n184), .B(n8), .C(n83), .Z(n284) );
  AOI22M1P U225 ( .A(\Queue[1][21] ), .B(n95), .C(n94), .D(Packet_Out[21]), 
        .Z(n84) );
  OAI21M1P U226 ( .A(n186), .B(n8), .C(n84), .Z(n283) );
  AOI22M1P U227 ( .A(\Queue[1][22] ), .B(n95), .C(n94), .D(Packet_Out[22]), 
        .Z(n85) );
  OAI21M1P U228 ( .A(n188), .B(n8), .C(n85), .Z(n282) );
  AOI22M1P U229 ( .A(\Queue[1][23] ), .B(n95), .C(n94), .D(Packet_Out[23]), 
        .Z(n86) );
  OAI21M1P U230 ( .A(n190), .B(n8), .C(n86), .Z(n281) );
  AOI22M1P U231 ( .A(\Queue[1][24] ), .B(n95), .C(n94), .D(Packet_Out[24]), 
        .Z(n87) );
  OAI21M1P U232 ( .A(n192), .B(n8), .C(n87), .Z(n280) );
  AOI22M1P U233 ( .A(\Queue[1][25] ), .B(n95), .C(n94), .D(Packet_Out[25]), 
        .Z(n88) );
  OAI21M1P U234 ( .A(n194), .B(n8), .C(n88), .Z(n279) );
  AOI22M1P U235 ( .A(\Queue[1][26] ), .B(n95), .C(n94), .D(Packet_Out[26]), 
        .Z(n89) );
  OAI21M1P U236 ( .A(n196), .B(n8), .C(n89), .Z(n278) );
  AOI22M1P U237 ( .A(\Queue[1][27] ), .B(n95), .C(n94), .D(Packet_Out[27]), 
        .Z(n90) );
  OAI21M1P U238 ( .A(n198), .B(n8), .C(n90), .Z(n277) );
  AOI22M1P U239 ( .A(\Queue[1][28] ), .B(n95), .C(n94), .D(Packet_Out[28]), 
        .Z(n91) );
  OAI21M1P U240 ( .A(n200), .B(n8), .C(n91), .Z(n276) );
  AOI22M1P U241 ( .A(\Queue[1][29] ), .B(n95), .C(n94), .D(Packet_Out[29]), 
        .Z(n92) );
  OAI21M1P U242 ( .A(n202), .B(n8), .C(n92), .Z(n275) );
  AOI22M1P U243 ( .A(\Queue[1][30] ), .B(n95), .C(n94), .D(Packet_Out[30]), 
        .Z(n93) );
  OAI21M1P U244 ( .A(n204), .B(n7), .C(n93), .Z(n274) );
  AOI22M1P U245 ( .A(\Queue[1][31] ), .B(n95), .C(n94), .D(Packet_Out[31]), 
        .Z(n96) );
  OAI21M1P U246 ( .A(n373), .B(n8), .C(n96), .Z(n273) );
  OA21M1P U247 ( .A(n139), .B(n100), .C(Rst_n), .Z(n98) );
  ND2M1P U248 ( .A(n99), .B(n137), .Z(n138) );
  AOI22M1P U249 ( .A(\Queue[2][0] ), .B(n133), .C(\Queue[3][0] ), .D(n132), 
        .Z(n101) );
  OAI21M1P U250 ( .A(n144), .B(n9), .C(n101), .Z(n272) );
  AOI22M1P U251 ( .A(\Queue[2][1] ), .B(n133), .C(n132), .D(\Queue[3][1] ), 
        .Z(n102) );
  OAI21M1P U252 ( .A(n146), .B(n9), .C(n102), .Z(n271) );
  AOI22M1P U253 ( .A(\Queue[2][2] ), .B(n133), .C(n132), .D(\Queue[3][2] ), 
        .Z(n103) );
  OAI21M1P U254 ( .A(n148), .B(n9), .C(n103), .Z(n270) );
  AOI22M1P U255 ( .A(\Queue[2][3] ), .B(n133), .C(n132), .D(\Queue[3][3] ), 
        .Z(n104) );
  OAI21M1P U256 ( .A(n150), .B(n9), .C(n104), .Z(n269) );
  AOI22M1P U257 ( .A(\Queue[2][4] ), .B(n133), .C(n132), .D(\Queue[3][4] ), 
        .Z(n105) );
  OAI21M1P U258 ( .A(n152), .B(n9), .C(n105), .Z(n268) );
  AOI22M1P U259 ( .A(\Queue[2][5] ), .B(n133), .C(n132), .D(\Queue[3][5] ), 
        .Z(n106) );
  OAI21M1P U260 ( .A(n154), .B(n9), .C(n106), .Z(n267) );
  AOI22M1P U261 ( .A(\Queue[2][6] ), .B(n133), .C(n132), .D(\Queue[3][6] ), 
        .Z(n107) );
  OAI21M1P U262 ( .A(n156), .B(n9), .C(n107), .Z(n266) );
  AOI22M1P U263 ( .A(\Queue[2][7] ), .B(n133), .C(n132), .D(\Queue[3][7] ), 
        .Z(n108) );
  OAI21M1P U264 ( .A(n158), .B(n9), .C(n108), .Z(n265) );
  AOI22M1P U265 ( .A(\Queue[2][8] ), .B(n133), .C(n132), .D(\Queue[3][8] ), 
        .Z(n109) );
  OAI21M1P U266 ( .A(n160), .B(n9), .C(n109), .Z(n264) );
  AOI22M1P U267 ( .A(\Queue[2][9] ), .B(n133), .C(n132), .D(\Queue[3][9] ), 
        .Z(n110) );
  OAI21M1P U268 ( .A(n162), .B(n9), .C(n110), .Z(n263) );
  AOI22M1P U269 ( .A(\Queue[2][10] ), .B(n133), .C(n132), .D(\Queue[3][10] ), 
        .Z(n111) );
  OAI21M1P U270 ( .A(n164), .B(n9), .C(n111), .Z(n262) );
  AOI22M1P U271 ( .A(\Queue[2][11] ), .B(n133), .C(n132), .D(\Queue[3][11] ), 
        .Z(n112) );
  OAI21M1P U272 ( .A(n166), .B(n9), .C(n112), .Z(n261) );
  AOI22M1P U273 ( .A(\Queue[2][12] ), .B(n133), .C(n132), .D(\Queue[3][12] ), 
        .Z(n113) );
  OAI21M1P U274 ( .A(n168), .B(n9), .C(n113), .Z(n260) );
  AOI22M1P U275 ( .A(\Queue[2][13] ), .B(n133), .C(n132), .D(\Queue[3][13] ), 
        .Z(n114) );
  OAI21M1P U276 ( .A(n170), .B(n9), .C(n114), .Z(n259) );
  AOI22M1P U277 ( .A(\Queue[2][14] ), .B(n133), .C(n132), .D(\Queue[3][14] ), 
        .Z(n115) );
  OAI21M1P U278 ( .A(n172), .B(n9), .C(n115), .Z(n258) );
  AOI22M1P U279 ( .A(\Queue[2][15] ), .B(n133), .C(n132), .D(\Queue[3][15] ), 
        .Z(n116) );
  OAI21M1P U280 ( .A(n174), .B(n9), .C(n116), .Z(n257) );
  AOI22M1P U281 ( .A(\Queue[2][16] ), .B(n133), .C(n132), .D(\Queue[3][16] ), 
        .Z(n117) );
  OAI21M1P U282 ( .A(n176), .B(n9), .C(n117), .Z(n256) );
  AOI22M1P U283 ( .A(\Queue[2][17] ), .B(n133), .C(n132), .D(\Queue[3][17] ), 
        .Z(n118) );
  OAI21M1P U284 ( .A(n178), .B(n9), .C(n118), .Z(n255) );
  AOI22M1P U285 ( .A(\Queue[2][18] ), .B(n133), .C(n132), .D(\Queue[3][18] ), 
        .Z(n119) );
  OAI21M1P U286 ( .A(n180), .B(n9), .C(n119), .Z(n254) );
  AOI22M1P U287 ( .A(\Queue[2][19] ), .B(n133), .C(n132), .D(\Queue[3][19] ), 
        .Z(n120) );
  OAI21M1P U288 ( .A(n182), .B(n9), .C(n120), .Z(n253) );
  AOI22M1P U289 ( .A(\Queue[2][20] ), .B(n133), .C(n132), .D(\Queue[3][20] ), 
        .Z(n121) );
  OAI21M1P U290 ( .A(n184), .B(n10), .C(n121), .Z(n252) );
  AOI22M1P U291 ( .A(\Queue[2][21] ), .B(n133), .C(n132), .D(\Queue[3][21] ), 
        .Z(n122) );
  OAI21M1P U292 ( .A(n186), .B(n10), .C(n122), .Z(n251) );
  AOI22M1P U293 ( .A(\Queue[2][22] ), .B(n133), .C(n132), .D(\Queue[3][22] ), 
        .Z(n123) );
  OAI21M1P U294 ( .A(n188), .B(n10), .C(n123), .Z(n250) );
  AOI22M1P U295 ( .A(\Queue[2][23] ), .B(n133), .C(n132), .D(\Queue[3][23] ), 
        .Z(n124) );
  OAI21M1P U296 ( .A(n190), .B(n10), .C(n124), .Z(n249) );
  AOI22M1P U297 ( .A(\Queue[2][24] ), .B(n133), .C(n132), .D(\Queue[3][24] ), 
        .Z(n125) );
  OAI21M1P U298 ( .A(n192), .B(n10), .C(n125), .Z(n248) );
  AOI22M1P U299 ( .A(\Queue[2][25] ), .B(n133), .C(n132), .D(\Queue[3][25] ), 
        .Z(n126) );
  OAI21M1P U300 ( .A(n194), .B(n10), .C(n126), .Z(n247) );
  AOI22M1P U301 ( .A(\Queue[2][26] ), .B(n133), .C(n132), .D(\Queue[3][26] ), 
        .Z(n127) );
  OAI21M1P U302 ( .A(n196), .B(n10), .C(n127), .Z(n246) );
  AOI22M1P U303 ( .A(\Queue[2][27] ), .B(n133), .C(n132), .D(\Queue[3][27] ), 
        .Z(n128) );
  OAI21M1P U304 ( .A(n198), .B(n10), .C(n128), .Z(n245) );
  AOI22M1P U305 ( .A(\Queue[2][28] ), .B(n133), .C(n132), .D(\Queue[3][28] ), 
        .Z(n129) );
  OAI21M1P U306 ( .A(n200), .B(n10), .C(n129), .Z(n244) );
  AOI22M1P U307 ( .A(\Queue[2][29] ), .B(n133), .C(n132), .D(\Queue[3][29] ), 
        .Z(n130) );
  OAI21M1P U308 ( .A(n202), .B(n10), .C(n130), .Z(n243) );
  AOI22M1P U309 ( .A(\Queue[2][30] ), .B(n133), .C(n132), .D(\Queue[3][30] ), 
        .Z(n131) );
  OAI21M1P U310 ( .A(n204), .B(n9), .C(n131), .Z(n242) );
  AOI22M1P U311 ( .A(\Queue[2][31] ), .B(n133), .C(n132), .D(\Queue[3][31] ), 
        .Z(n134) );
  OAI21M1P U312 ( .A(n373), .B(n10), .C(n134), .Z(n241) );
  OA21M1P U313 ( .A(n139), .B(n140), .C(Rst_n), .Z(n136) );
  AOI21M1P U314 ( .A(Write_Ack), .B(n142), .C(n141), .Z(n205) );
  AOI22M1P U315 ( .A(\Queue[3][0] ), .B(n206), .C(n4), .D(\Queue[4][0] ), .Z(
        n143) );
  OAI21M1P U316 ( .A(n144), .B(n11), .C(n143), .Z(n240) );
  AOI22M1P U317 ( .A(\Queue[3][1] ), .B(n206), .C(n4), .D(\Queue[4][1] ), .Z(
        n145) );
  OAI21M1P U318 ( .A(n146), .B(n11), .C(n145), .Z(n239) );
  AOI22M1P U319 ( .A(\Queue[3][2] ), .B(n206), .C(n4), .D(\Queue[4][2] ), .Z(
        n147) );
  OAI21M1P U320 ( .A(n148), .B(n11), .C(n147), .Z(n238) );
  AOI22M1P U321 ( .A(\Queue[3][3] ), .B(n206), .C(n4), .D(\Queue[4][3] ), .Z(
        n149) );
  OAI21M1P U322 ( .A(n150), .B(n11), .C(n149), .Z(n237) );
  AOI22M1P U323 ( .A(\Queue[3][4] ), .B(n206), .C(n4), .D(\Queue[4][4] ), .Z(
        n151) );
  OAI21M1P U324 ( .A(n152), .B(n11), .C(n151), .Z(n236) );
  AOI22M1P U325 ( .A(\Queue[3][5] ), .B(n206), .C(n4), .D(\Queue[4][5] ), .Z(
        n153) );
  OAI21M1P U326 ( .A(n154), .B(n11), .C(n153), .Z(n235) );
  AOI22M1P U327 ( .A(\Queue[3][6] ), .B(n206), .C(n4), .D(\Queue[4][6] ), .Z(
        n155) );
  OAI21M1P U328 ( .A(n156), .B(n11), .C(n155), .Z(n234) );
  AOI22M1P U329 ( .A(\Queue[3][7] ), .B(n206), .C(n4), .D(\Queue[4][7] ), .Z(
        n157) );
  OAI21M1P U330 ( .A(n158), .B(n11), .C(n157), .Z(n233) );
  AOI22M1P U331 ( .A(\Queue[3][8] ), .B(n206), .C(n4), .D(\Queue[4][8] ), .Z(
        n159) );
  OAI21M1P U332 ( .A(n160), .B(n11), .C(n159), .Z(n232) );
  AOI22M1P U333 ( .A(\Queue[3][9] ), .B(n206), .C(n4), .D(\Queue[4][9] ), .Z(
        n161) );
  OAI21M1P U334 ( .A(n162), .B(n11), .C(n161), .Z(n231) );
  AOI22M1P U335 ( .A(\Queue[3][10] ), .B(n206), .C(n4), .D(\Queue[4][10] ), 
        .Z(n163) );
  OAI21M1P U336 ( .A(n164), .B(n11), .C(n163), .Z(n230) );
  AOI22M1P U337 ( .A(\Queue[3][11] ), .B(n206), .C(n4), .D(\Queue[4][11] ), 
        .Z(n165) );
  OAI21M1P U338 ( .A(n166), .B(n11), .C(n165), .Z(n229) );
  AOI22M1P U339 ( .A(\Queue[3][12] ), .B(n206), .C(n4), .D(\Queue[4][12] ), 
        .Z(n167) );
  OAI21M1P U340 ( .A(n168), .B(n11), .C(n167), .Z(n228) );
  AOI22M1P U341 ( .A(\Queue[3][13] ), .B(n206), .C(n4), .D(\Queue[4][13] ), 
        .Z(n169) );
  OAI21M1P U342 ( .A(n170), .B(n11), .C(n169), .Z(n227) );
  AOI22M1P U343 ( .A(\Queue[3][14] ), .B(n206), .C(n4), .D(\Queue[4][14] ), 
        .Z(n171) );
  OAI21M1P U344 ( .A(n172), .B(n11), .C(n171), .Z(n226) );
  AOI22M1P U345 ( .A(\Queue[3][15] ), .B(n206), .C(n4), .D(\Queue[4][15] ), 
        .Z(n173) );
  OAI21M1P U346 ( .A(n174), .B(n11), .C(n173), .Z(n225) );
  AOI22M1P U347 ( .A(\Queue[3][16] ), .B(n206), .C(n4), .D(\Queue[4][16] ), 
        .Z(n175) );
  OAI21M1P U348 ( .A(n176), .B(n11), .C(n175), .Z(n224) );
  AOI22M1P U349 ( .A(\Queue[3][17] ), .B(n206), .C(n4), .D(\Queue[4][17] ), 
        .Z(n177) );
  OAI21M1P U350 ( .A(n178), .B(n11), .C(n177), .Z(n223) );
  AOI22M1P U351 ( .A(\Queue[3][18] ), .B(n206), .C(n4), .D(\Queue[4][18] ), 
        .Z(n179) );
  OAI21M1P U352 ( .A(n180), .B(n11), .C(n179), .Z(n222) );
  AOI22M1P U353 ( .A(\Queue[3][19] ), .B(n206), .C(n4), .D(\Queue[4][19] ), 
        .Z(n181) );
  OAI21M1P U354 ( .A(n182), .B(n11), .C(n181), .Z(n221) );
  AOI22M1P U355 ( .A(\Queue[3][20] ), .B(n206), .C(n4), .D(\Queue[4][20] ), 
        .Z(n183) );
  OAI21M1P U356 ( .A(n184), .B(n12), .C(n183), .Z(n220) );
  AOI22M1P U357 ( .A(\Queue[3][21] ), .B(n206), .C(n4), .D(\Queue[4][21] ), 
        .Z(n185) );
  OAI21M1P U358 ( .A(n186), .B(n12), .C(n185), .Z(n219) );
  AOI22M1P U359 ( .A(\Queue[3][22] ), .B(n206), .C(n4), .D(\Queue[4][22] ), 
        .Z(n187) );
  OAI21M1P U360 ( .A(n188), .B(n12), .C(n187), .Z(n218) );
  AOI22M1P U361 ( .A(\Queue[3][23] ), .B(n206), .C(n4), .D(\Queue[4][23] ), 
        .Z(n189) );
  OAI21M1P U362 ( .A(n190), .B(n12), .C(n189), .Z(n217) );
  AOI22M1P U363 ( .A(\Queue[3][24] ), .B(n206), .C(n4), .D(\Queue[4][24] ), 
        .Z(n191) );
  OAI21M1P U364 ( .A(n192), .B(n12), .C(n191), .Z(n216) );
  AOI22M1P U365 ( .A(\Queue[3][25] ), .B(n206), .C(n4), .D(\Queue[4][25] ), 
        .Z(n193) );
  OAI21M1P U366 ( .A(n194), .B(n12), .C(n193), .Z(n215) );
  AOI22M1P U367 ( .A(\Queue[3][26] ), .B(n206), .C(n4), .D(\Queue[4][26] ), 
        .Z(n195) );
  OAI21M1P U368 ( .A(n196), .B(n12), .C(n195), .Z(n214) );
  AOI22M1P U369 ( .A(\Queue[3][27] ), .B(n206), .C(n4), .D(\Queue[4][27] ), 
        .Z(n197) );
  OAI21M1P U370 ( .A(n198), .B(n12), .C(n197), .Z(n213) );
  AOI22M1P U371 ( .A(\Queue[3][28] ), .B(n206), .C(n4), .D(\Queue[4][28] ), 
        .Z(n199) );
  OAI21M1P U372 ( .A(n200), .B(n12), .C(n199), .Z(n212) );
  AOI22M1P U373 ( .A(\Queue[3][29] ), .B(n206), .C(n4), .D(\Queue[4][29] ), 
        .Z(n201) );
  OAI21M1P U374 ( .A(n202), .B(n12), .C(n201), .Z(n211) );
  AOI22M1P U375 ( .A(\Queue[3][30] ), .B(n206), .C(n4), .D(\Queue[4][30] ), 
        .Z(n203) );
  OAI21M1P U376 ( .A(n204), .B(n11), .C(n203), .Z(n210) );
  AOI22M1P U377 ( .A(\Queue[3][31] ), .B(n206), .C(n4), .D(\Queue[4][31] ), 
        .Z(n207) );
  OAI21M1P U378 ( .A(n373), .B(n12), .C(n207), .Z(n209) );
endmodule


module Packet_Queue_1 ( Clk_r, Rst_n, Packet_In, Read_Ack, Write_Ack, 
        Packet_Out, Empty, Full );
  input [31:0] Packet_In;
  output [31:0] Packet_Out;
  input Clk_r, Rst_n, Read_Ack, Write_Ack;
  output Empty, Full;
  wire   \Queue[4][31] , \Queue[4][30] , \Queue[4][29] , \Queue[4][28] ,
         \Queue[4][27] , \Queue[4][26] , \Queue[4][25] , \Queue[4][24] ,
         \Queue[4][23] , \Queue[4][22] , \Queue[4][21] , \Queue[4][20] ,
         \Queue[4][19] , \Queue[4][18] , \Queue[4][17] , \Queue[4][16] ,
         \Queue[4][15] , \Queue[4][14] , \Queue[4][13] , \Queue[4][12] ,
         \Queue[4][11] , \Queue[4][10] , \Queue[4][9] , \Queue[4][8] ,
         \Queue[4][7] , \Queue[4][6] , \Queue[4][5] , \Queue[4][4] ,
         \Queue[4][3] , \Queue[4][2] , \Queue[4][1] , \Queue[4][0] ,
         \Queue[3][31] , \Queue[3][30] , \Queue[3][29] , \Queue[3][28] ,
         \Queue[3][27] , \Queue[3][26] , \Queue[3][25] , \Queue[3][24] ,
         \Queue[3][23] , \Queue[3][22] , \Queue[3][21] , \Queue[3][20] ,
         \Queue[3][19] , \Queue[3][18] , \Queue[3][17] , \Queue[3][16] ,
         \Queue[3][15] , \Queue[3][14] , \Queue[3][13] , \Queue[3][12] ,
         \Queue[3][11] , \Queue[3][10] , \Queue[3][9] , \Queue[3][8] ,
         \Queue[3][7] , \Queue[3][6] , \Queue[3][5] , \Queue[3][4] ,
         \Queue[3][3] , \Queue[3][2] , \Queue[3][1] , \Queue[3][0] ,
         \Queue[2][31] , \Queue[2][30] , \Queue[2][29] , \Queue[2][28] ,
         \Queue[2][27] , \Queue[2][26] , \Queue[2][25] , \Queue[2][24] ,
         \Queue[2][23] , \Queue[2][22] , \Queue[2][21] , \Queue[2][20] ,
         \Queue[2][19] , \Queue[2][18] , \Queue[2][17] , \Queue[2][16] ,
         \Queue[2][15] , \Queue[2][14] , \Queue[2][13] , \Queue[2][12] ,
         \Queue[2][11] , \Queue[2][10] , \Queue[2][9] , \Queue[2][8] ,
         \Queue[2][7] , \Queue[2][6] , \Queue[2][5] , \Queue[2][4] ,
         \Queue[2][3] , \Queue[2][2] , \Queue[2][1] , \Queue[2][0] ,
         \Queue[1][31] , \Queue[1][30] , \Queue[1][29] , \Queue[1][28] ,
         \Queue[1][27] , \Queue[1][26] , \Queue[1][25] , \Queue[1][24] ,
         \Queue[1][23] , \Queue[1][22] , \Queue[1][21] , \Queue[1][20] ,
         \Queue[1][19] , \Queue[1][18] , \Queue[1][17] , \Queue[1][16] ,
         \Queue[1][15] , \Queue[1][14] , \Queue[1][13] , \Queue[1][12] ,
         \Queue[1][11] , \Queue[1][10] , \Queue[1][9] , \Queue[1][8] ,
         \Queue[1][7] , \Queue[1][6] , \Queue[1][5] , \Queue[1][4] ,
         \Queue[1][3] , \Queue[1][2] , \Queue[1][1] , \Queue[1][0] , n3, n4,
         n5, n7, n8, n9, n10, n11, n12, n13, n14, n15, n16, n17, n18, n19, n20,
         n21, n22, n23, n24, n25, n26, n27, n28, n29, n30, n31, n32, n33, n34,
         n35, n36, n37, n38, n39, n40, n41, n42, n43, n44, n45, n46, n47, n48,
         n49, n50, n51, n52, n53, n54, n55, n56, n57, n58, n59, n60, n61, n62,
         n63, n64, n65, n66, n67, n68, n69, n70, n71, n72, n73, n74, n75, n76,
         n77, n78, n79, n80, n81, n82, n83, n84, n85, n86, n87, n88, n89, n90,
         n91, n92, n93, n94, n95, n96, n97, n98, n99, n100, n101, n102, n103,
         n104, n105, n106, n107, n108, n109, n110, n111, n112, n113, n114,
         n115, n116, n117, n118, n119, n120, n121, n122, n123, n124, n125,
         n126, n127, n128, n129, n130, n131, n132, n133, n134, n135, n136,
         n137, n138, n139, n140, n141, n142, n143, n144, n145, n146, n147,
         n148, n149, n150, n151, n152, n153, n154, n155, n156, n157, n158,
         n159, n160, n161, n162, n163, n164, n165, n166, n167, n168, n169,
         n170, n171, n172, n173, n174, n175, n176, n177, n178, n179, n180,
         n181, n182, n183, n184, n185, n186, n187, n188, n189, n190, n191,
         n192, n193, n194, n195, n196, n197, n198, n199, n200, n201, n202,
         n203, n204, n205, n206, n207, n208, n373, n374, n375, n376, n377,
         n378, n379, n380, n381, n382, n383, n384, n385, n386, n387, n388,
         n389, n390, n391, n392, n393, n394, n395, n396, n397, n398, n399,
         n400, n401, n402, n403, n404, n405, n406, n407, n408, n409, n410,
         n411, n412, n413, n414, n415, n416, n417, n418, n419, n420, n421,
         n422, n423, n424, n425, n426, n427, n428, n429, n430, n431, n432,
         n433, n434, n435, n436, n437, n438, n439, n440, n441, n442, n443,
         n444, n445, n446, n447, n448, n449, n450, n451, n452, n453, n454,
         n455, n456, n457, n458, n459, n460, n461, n462, n463, n464, n465,
         n466, n467, n468, n469, n470, n471, n472, n473, n474, n475, n476,
         n477, n478, n479, n480, n481, n482, n483, n484, n485, n486, n487,
         n488, n489, n490, n491, n492, n493, n494, n495, n496, n497, n498,
         n499, n500, n501, n502, n503, n504, n505, n506, n507, n508, n509,
         n510, n511, n512, n513, n514, n515, n516, n517, n518, n519, n520,
         n521, n522, n523, n524, n525, n526, n527, n528, n529, n530, n531,
         n532, n533, n534, n535, n536, n537, n538, n539, n540, n541;
  wire   [2:0] Count;

  FDN2QM1P \Count_reg[0]  ( .D(n376), .CPN(Clk_r), .CD(Rst_n), .Q(Count[0]) );
  FDN2QM1P \Count_reg[1]  ( .D(n377), .CPN(Clk_r), .CD(Rst_n), .Q(Count[1]) );
  FDN2QM1P \Count_reg[2]  ( .D(n378), .CPN(Clk_r), .CD(Rst_n), .Q(Count[2]) );
  FDN1QM1P \Queue_reg[4][30]  ( .D(n410), .CPN(Clk_r), .Q(\Queue[4][30] ) );
  FDN1QM1P \Queue_reg[3][30]  ( .D(n538), .CPN(Clk_r), .Q(\Queue[3][30] ) );
  FDN1QM1P \Queue_reg[2][30]  ( .D(n506), .CPN(Clk_r), .Q(\Queue[2][30] ) );
  FDN1QM1P \Queue_reg[4][29]  ( .D(n409), .CPN(Clk_r), .Q(\Queue[4][29] ) );
  FDN1QM1P \Queue_reg[3][29]  ( .D(n537), .CPN(Clk_r), .Q(\Queue[3][29] ) );
  FDN1QM1P \Queue_reg[2][29]  ( .D(n505), .CPN(Clk_r), .Q(\Queue[2][29] ) );
  FDN1QM1P \Queue_reg[4][28]  ( .D(n408), .CPN(Clk_r), .Q(\Queue[4][28] ) );
  FDN1QM1P \Queue_reg[3][28]  ( .D(n536), .CPN(Clk_r), .Q(\Queue[3][28] ) );
  FDN1QM1P \Queue_reg[2][28]  ( .D(n504), .CPN(Clk_r), .Q(\Queue[2][28] ) );
  FDN1QM1P \Queue_reg[4][27]  ( .D(n407), .CPN(Clk_r), .Q(\Queue[4][27] ) );
  FDN1QM1P \Queue_reg[3][27]  ( .D(n535), .CPN(Clk_r), .Q(\Queue[3][27] ) );
  FDN1QM1P \Queue_reg[2][27]  ( .D(n503), .CPN(Clk_r), .Q(\Queue[2][27] ) );
  FDN1QM1P \Queue_reg[4][26]  ( .D(n406), .CPN(Clk_r), .Q(\Queue[4][26] ) );
  FDN1QM1P \Queue_reg[3][26]  ( .D(n534), .CPN(Clk_r), .Q(\Queue[3][26] ) );
  FDN1QM1P \Queue_reg[2][26]  ( .D(n502), .CPN(Clk_r), .Q(\Queue[2][26] ) );
  FDN1QM1P \Queue_reg[4][25]  ( .D(n405), .CPN(Clk_r), .Q(\Queue[4][25] ) );
  FDN1QM1P \Queue_reg[3][25]  ( .D(n533), .CPN(Clk_r), .Q(\Queue[3][25] ) );
  FDN1QM1P \Queue_reg[2][25]  ( .D(n501), .CPN(Clk_r), .Q(\Queue[2][25] ) );
  FDN1QM1P \Queue_reg[4][24]  ( .D(n404), .CPN(Clk_r), .Q(\Queue[4][24] ) );
  FDN1QM1P \Queue_reg[3][24]  ( .D(n532), .CPN(Clk_r), .Q(\Queue[3][24] ) );
  FDN1QM1P \Queue_reg[2][24]  ( .D(n500), .CPN(Clk_r), .Q(\Queue[2][24] ) );
  FDN1QM1P \Queue_reg[4][23]  ( .D(n403), .CPN(Clk_r), .Q(\Queue[4][23] ) );
  FDN1QM1P \Queue_reg[3][23]  ( .D(n531), .CPN(Clk_r), .Q(\Queue[3][23] ) );
  FDN1QM1P \Queue_reg[2][23]  ( .D(n499), .CPN(Clk_r), .Q(\Queue[2][23] ) );
  FDN1QM1P \Queue_reg[4][22]  ( .D(n402), .CPN(Clk_r), .Q(\Queue[4][22] ) );
  FDN1QM1P \Queue_reg[3][22]  ( .D(n530), .CPN(Clk_r), .Q(\Queue[3][22] ) );
  FDN1QM1P \Queue_reg[2][22]  ( .D(n498), .CPN(Clk_r), .Q(\Queue[2][22] ) );
  FDN1QM1P \Queue_reg[4][21]  ( .D(n401), .CPN(Clk_r), .Q(\Queue[4][21] ) );
  FDN1QM1P \Queue_reg[3][21]  ( .D(n529), .CPN(Clk_r), .Q(\Queue[3][21] ) );
  FDN1QM1P \Queue_reg[2][21]  ( .D(n497), .CPN(Clk_r), .Q(\Queue[2][21] ) );
  FDN1QM1P \Queue_reg[4][20]  ( .D(n400), .CPN(Clk_r), .Q(\Queue[4][20] ) );
  FDN1QM1P \Queue_reg[3][20]  ( .D(n528), .CPN(Clk_r), .Q(\Queue[3][20] ) );
  FDN1QM1P \Queue_reg[2][20]  ( .D(n496), .CPN(Clk_r), .Q(\Queue[2][20] ) );
  FDN1QM1P \Queue_reg[4][19]  ( .D(n399), .CPN(Clk_r), .Q(\Queue[4][19] ) );
  FDN1QM1P \Queue_reg[3][19]  ( .D(n527), .CPN(Clk_r), .Q(\Queue[3][19] ) );
  FDN1QM1P \Queue_reg[2][19]  ( .D(n495), .CPN(Clk_r), .Q(\Queue[2][19] ) );
  FDN1QM1P \Queue_reg[4][18]  ( .D(n398), .CPN(Clk_r), .Q(\Queue[4][18] ) );
  FDN1QM1P \Queue_reg[3][18]  ( .D(n526), .CPN(Clk_r), .Q(\Queue[3][18] ) );
  FDN1QM1P \Queue_reg[2][18]  ( .D(n494), .CPN(Clk_r), .Q(\Queue[2][18] ) );
  FDN1QM1P \Queue_reg[4][17]  ( .D(n397), .CPN(Clk_r), .Q(\Queue[4][17] ) );
  FDN1QM1P \Queue_reg[3][17]  ( .D(n525), .CPN(Clk_r), .Q(\Queue[3][17] ) );
  FDN1QM1P \Queue_reg[2][17]  ( .D(n493), .CPN(Clk_r), .Q(\Queue[2][17] ) );
  FDN1QM1P \Queue_reg[4][16]  ( .D(n396), .CPN(Clk_r), .Q(\Queue[4][16] ) );
  FDN1QM1P \Queue_reg[3][16]  ( .D(n524), .CPN(Clk_r), .Q(\Queue[3][16] ) );
  FDN1QM1P \Queue_reg[2][16]  ( .D(n492), .CPN(Clk_r), .Q(\Queue[2][16] ) );
  FDN1QM1P \Queue_reg[4][15]  ( .D(n395), .CPN(Clk_r), .Q(\Queue[4][15] ) );
  FDN1QM1P \Queue_reg[3][15]  ( .D(n523), .CPN(Clk_r), .Q(\Queue[3][15] ) );
  FDN1QM1P \Queue_reg[2][15]  ( .D(n491), .CPN(Clk_r), .Q(\Queue[2][15] ) );
  FDN1QM1P \Queue_reg[4][14]  ( .D(n394), .CPN(Clk_r), .Q(\Queue[4][14] ) );
  FDN1QM1P \Queue_reg[3][14]  ( .D(n522), .CPN(Clk_r), .Q(\Queue[3][14] ) );
  FDN1QM1P \Queue_reg[2][14]  ( .D(n490), .CPN(Clk_r), .Q(\Queue[2][14] ) );
  FDN1QM1P \Queue_reg[4][13]  ( .D(n393), .CPN(Clk_r), .Q(\Queue[4][13] ) );
  FDN1QM1P \Queue_reg[3][13]  ( .D(n521), .CPN(Clk_r), .Q(\Queue[3][13] ) );
  FDN1QM1P \Queue_reg[2][13]  ( .D(n489), .CPN(Clk_r), .Q(\Queue[2][13] ) );
  FDN1QM1P \Queue_reg[4][12]  ( .D(n392), .CPN(Clk_r), .Q(\Queue[4][12] ) );
  FDN1QM1P \Queue_reg[3][12]  ( .D(n520), .CPN(Clk_r), .Q(\Queue[3][12] ) );
  FDN1QM1P \Queue_reg[2][12]  ( .D(n488), .CPN(Clk_r), .Q(\Queue[2][12] ) );
  FDN1QM1P \Queue_reg[4][11]  ( .D(n391), .CPN(Clk_r), .Q(\Queue[4][11] ) );
  FDN1QM1P \Queue_reg[3][11]  ( .D(n519), .CPN(Clk_r), .Q(\Queue[3][11] ) );
  FDN1QM1P \Queue_reg[2][11]  ( .D(n487), .CPN(Clk_r), .Q(\Queue[2][11] ) );
  FDN1QM1P \Queue_reg[4][10]  ( .D(n390), .CPN(Clk_r), .Q(\Queue[4][10] ) );
  FDN1QM1P \Queue_reg[3][10]  ( .D(n518), .CPN(Clk_r), .Q(\Queue[3][10] ) );
  FDN1QM1P \Queue_reg[2][10]  ( .D(n486), .CPN(Clk_r), .Q(\Queue[2][10] ) );
  FDN1QM1P \Queue_reg[4][9]  ( .D(n389), .CPN(Clk_r), .Q(\Queue[4][9] ) );
  FDN1QM1P \Queue_reg[3][9]  ( .D(n517), .CPN(Clk_r), .Q(\Queue[3][9] ) );
  FDN1QM1P \Queue_reg[2][9]  ( .D(n485), .CPN(Clk_r), .Q(\Queue[2][9] ) );
  FDN1QM1P \Queue_reg[4][8]  ( .D(n388), .CPN(Clk_r), .Q(\Queue[4][8] ) );
  FDN1QM1P \Queue_reg[3][8]  ( .D(n516), .CPN(Clk_r), .Q(\Queue[3][8] ) );
  FDN1QM1P \Queue_reg[2][8]  ( .D(n484), .CPN(Clk_r), .Q(\Queue[2][8] ) );
  FDN1QM1P \Queue_reg[4][7]  ( .D(n387), .CPN(Clk_r), .Q(\Queue[4][7] ) );
  FDN1QM1P \Queue_reg[3][7]  ( .D(n515), .CPN(Clk_r), .Q(\Queue[3][7] ) );
  FDN1QM1P \Queue_reg[2][7]  ( .D(n483), .CPN(Clk_r), .Q(\Queue[2][7] ) );
  FDN1QM1P \Queue_reg[4][6]  ( .D(n386), .CPN(Clk_r), .Q(\Queue[4][6] ) );
  FDN1QM1P \Queue_reg[3][6]  ( .D(n514), .CPN(Clk_r), .Q(\Queue[3][6] ) );
  FDN1QM1P \Queue_reg[2][6]  ( .D(n482), .CPN(Clk_r), .Q(\Queue[2][6] ) );
  FDN1QM1P \Queue_reg[4][5]  ( .D(n385), .CPN(Clk_r), .Q(\Queue[4][5] ) );
  FDN1QM1P \Queue_reg[3][5]  ( .D(n513), .CPN(Clk_r), .Q(\Queue[3][5] ) );
  FDN1QM1P \Queue_reg[2][5]  ( .D(n481), .CPN(Clk_r), .Q(\Queue[2][5] ) );
  FDN1QM1P \Queue_reg[4][4]  ( .D(n384), .CPN(Clk_r), .Q(\Queue[4][4] ) );
  FDN1QM1P \Queue_reg[3][4]  ( .D(n512), .CPN(Clk_r), .Q(\Queue[3][4] ) );
  FDN1QM1P \Queue_reg[2][4]  ( .D(n480), .CPN(Clk_r), .Q(\Queue[2][4] ) );
  FDN1QM1P \Queue_reg[4][3]  ( .D(n383), .CPN(Clk_r), .Q(\Queue[4][3] ) );
  FDN1QM1P \Queue_reg[3][3]  ( .D(n511), .CPN(Clk_r), .Q(\Queue[3][3] ) );
  FDN1QM1P \Queue_reg[2][3]  ( .D(n479), .CPN(Clk_r), .Q(\Queue[2][3] ) );
  FDN1QM1P \Queue_reg[4][2]  ( .D(n382), .CPN(Clk_r), .Q(\Queue[4][2] ) );
  FDN1QM1P \Queue_reg[3][2]  ( .D(n510), .CPN(Clk_r), .Q(\Queue[3][2] ) );
  FDN1QM1P \Queue_reg[2][2]  ( .D(n478), .CPN(Clk_r), .Q(\Queue[2][2] ) );
  FDN1QM1P \Queue_reg[4][1]  ( .D(n381), .CPN(Clk_r), .Q(\Queue[4][1] ) );
  FDN1QM1P \Queue_reg[3][1]  ( .D(n509), .CPN(Clk_r), .Q(\Queue[3][1] ) );
  FDN1QM1P \Queue_reg[2][1]  ( .D(n477), .CPN(Clk_r), .Q(\Queue[2][1] ) );
  FDN1QM1P \Queue_reg[4][0]  ( .D(n380), .CPN(Clk_r), .Q(\Queue[4][0] ) );
  FDN1QM1P \Queue_reg[3][0]  ( .D(n508), .CPN(Clk_r), .Q(\Queue[3][0] ) );
  FDN1QM1P \Queue_reg[2][0]  ( .D(n476), .CPN(Clk_r), .Q(\Queue[2][0] ) );
  FDN1QM1P \Queue_reg[4][31]  ( .D(n379), .CPN(Clk_r), .Q(\Queue[4][31] ) );
  FDN1QM1P \Queue_reg[3][31]  ( .D(n539), .CPN(Clk_r), .Q(\Queue[3][31] ) );
  FDN1QM1P \Queue_reg[2][31]  ( .D(n507), .CPN(Clk_r), .Q(\Queue[2][31] ) );
  FDN1QM1P \Queue_reg[1][31]  ( .D(n443), .CPN(Clk_r), .Q(\Queue[1][31] ) );
  FDN1QM1P \Queue_reg[0][31]  ( .D(n475), .CPN(Clk_r), .Q(Packet_Out[31]) );
  FDN1QM1P \Queue_reg[1][30]  ( .D(n442), .CPN(Clk_r), .Q(\Queue[1][30] ) );
  FDN1QM1P \Queue_reg[0][30]  ( .D(n474), .CPN(Clk_r), .Q(Packet_Out[30]) );
  FDN1QM1P \Queue_reg[1][29]  ( .D(n441), .CPN(Clk_r), .Q(\Queue[1][29] ) );
  FDN1QM1P \Queue_reg[0][29]  ( .D(n473), .CPN(Clk_r), .Q(Packet_Out[29]) );
  FDN1QM1P \Queue_reg[1][28]  ( .D(n440), .CPN(Clk_r), .Q(\Queue[1][28] ) );
  FDN1QM1P \Queue_reg[0][28]  ( .D(n472), .CPN(Clk_r), .Q(Packet_Out[28]) );
  FDN1QM1P \Queue_reg[1][27]  ( .D(n439), .CPN(Clk_r), .Q(\Queue[1][27] ) );
  FDN1QM1P \Queue_reg[0][27]  ( .D(n471), .CPN(Clk_r), .Q(Packet_Out[27]) );
  FDN1QM1P \Queue_reg[1][26]  ( .D(n438), .CPN(Clk_r), .Q(\Queue[1][26] ) );
  FDN1QM1P \Queue_reg[0][26]  ( .D(n470), .CPN(Clk_r), .Q(Packet_Out[26]) );
  FDN1QM1P \Queue_reg[1][25]  ( .D(n437), .CPN(Clk_r), .Q(\Queue[1][25] ) );
  FDN1QM1P \Queue_reg[0][25]  ( .D(n469), .CPN(Clk_r), .Q(Packet_Out[25]) );
  FDN1QM1P \Queue_reg[1][24]  ( .D(n436), .CPN(Clk_r), .Q(\Queue[1][24] ) );
  FDN1QM1P \Queue_reg[0][24]  ( .D(n468), .CPN(Clk_r), .Q(Packet_Out[24]) );
  FDN1QM1P \Queue_reg[1][23]  ( .D(n435), .CPN(Clk_r), .Q(\Queue[1][23] ) );
  FDN1QM1P \Queue_reg[0][23]  ( .D(n467), .CPN(Clk_r), .Q(Packet_Out[23]) );
  FDN1QM1P \Queue_reg[1][22]  ( .D(n434), .CPN(Clk_r), .Q(\Queue[1][22] ) );
  FDN1QM1P \Queue_reg[0][22]  ( .D(n466), .CPN(Clk_r), .Q(Packet_Out[22]) );
  FDN1QM1P \Queue_reg[1][21]  ( .D(n433), .CPN(Clk_r), .Q(\Queue[1][21] ) );
  FDN1QM1P \Queue_reg[0][21]  ( .D(n465), .CPN(Clk_r), .Q(Packet_Out[21]) );
  FDN1QM1P \Queue_reg[1][20]  ( .D(n432), .CPN(Clk_r), .Q(\Queue[1][20] ) );
  FDN1QM1P \Queue_reg[0][20]  ( .D(n464), .CPN(Clk_r), .Q(Packet_Out[20]) );
  FDN1QM1P \Queue_reg[1][19]  ( .D(n431), .CPN(Clk_r), .Q(\Queue[1][19] ) );
  FDN1QM1P \Queue_reg[0][19]  ( .D(n463), .CPN(Clk_r), .Q(Packet_Out[19]) );
  FDN1QM1P \Queue_reg[1][18]  ( .D(n430), .CPN(Clk_r), .Q(\Queue[1][18] ) );
  FDN1QM1P \Queue_reg[0][18]  ( .D(n462), .CPN(Clk_r), .Q(Packet_Out[18]) );
  FDN1QM1P \Queue_reg[1][17]  ( .D(n429), .CPN(Clk_r), .Q(\Queue[1][17] ) );
  FDN1QM1P \Queue_reg[0][17]  ( .D(n461), .CPN(Clk_r), .Q(Packet_Out[17]) );
  FDN1QM1P \Queue_reg[1][16]  ( .D(n428), .CPN(Clk_r), .Q(\Queue[1][16] ) );
  FDN1QM1P \Queue_reg[0][16]  ( .D(n460), .CPN(Clk_r), .Q(Packet_Out[16]) );
  FDN1QM1P \Queue_reg[1][15]  ( .D(n427), .CPN(Clk_r), .Q(\Queue[1][15] ) );
  FDN1QM1P \Queue_reg[0][15]  ( .D(n459), .CPN(Clk_r), .Q(Packet_Out[15]) );
  FDN1QM1P \Queue_reg[1][14]  ( .D(n426), .CPN(Clk_r), .Q(\Queue[1][14] ) );
  FDN1QM1P \Queue_reg[0][14]  ( .D(n458), .CPN(Clk_r), .Q(Packet_Out[14]) );
  FDN1QM1P \Queue_reg[1][13]  ( .D(n425), .CPN(Clk_r), .Q(\Queue[1][13] ) );
  FDN1QM1P \Queue_reg[0][13]  ( .D(n457), .CPN(Clk_r), .Q(Packet_Out[13]) );
  FDN1QM1P \Queue_reg[1][12]  ( .D(n424), .CPN(Clk_r), .Q(\Queue[1][12] ) );
  FDN1QM1P \Queue_reg[0][12]  ( .D(n456), .CPN(Clk_r), .Q(Packet_Out[12]) );
  FDN1QM1P \Queue_reg[1][11]  ( .D(n423), .CPN(Clk_r), .Q(\Queue[1][11] ) );
  FDN1QM1P \Queue_reg[0][11]  ( .D(n455), .CPN(Clk_r), .Q(Packet_Out[11]) );
  FDN1QM1P \Queue_reg[1][10]  ( .D(n422), .CPN(Clk_r), .Q(\Queue[1][10] ) );
  FDN1QM1P \Queue_reg[0][10]  ( .D(n454), .CPN(Clk_r), .Q(Packet_Out[10]) );
  FDN1QM1P \Queue_reg[1][9]  ( .D(n421), .CPN(Clk_r), .Q(\Queue[1][9] ) );
  FDN1QM1P \Queue_reg[0][9]  ( .D(n453), .CPN(Clk_r), .Q(Packet_Out[9]) );
  FDN1QM1P \Queue_reg[1][8]  ( .D(n420), .CPN(Clk_r), .Q(\Queue[1][8] ) );
  FDN1QM1P \Queue_reg[0][8]  ( .D(n452), .CPN(Clk_r), .Q(Packet_Out[8]) );
  FDN1QM1P \Queue_reg[1][7]  ( .D(n419), .CPN(Clk_r), .Q(\Queue[1][7] ) );
  FDN1QM1P \Queue_reg[0][7]  ( .D(n451), .CPN(Clk_r), .Q(Packet_Out[7]) );
  FDN1QM1P \Queue_reg[1][6]  ( .D(n418), .CPN(Clk_r), .Q(\Queue[1][6] ) );
  FDN1QM1P \Queue_reg[0][6]  ( .D(n450), .CPN(Clk_r), .Q(Packet_Out[6]) );
  FDN1QM1P \Queue_reg[1][5]  ( .D(n417), .CPN(Clk_r), .Q(\Queue[1][5] ) );
  FDN1QM1P \Queue_reg[0][5]  ( .D(n449), .CPN(Clk_r), .Q(Packet_Out[5]) );
  FDN1QM1P \Queue_reg[1][4]  ( .D(n416), .CPN(Clk_r), .Q(\Queue[1][4] ) );
  FDN1QM1P \Queue_reg[0][4]  ( .D(n448), .CPN(Clk_r), .Q(Packet_Out[4]) );
  FDN1QM1P \Queue_reg[1][3]  ( .D(n415), .CPN(Clk_r), .Q(\Queue[1][3] ) );
  FDN1QM1P \Queue_reg[0][3]  ( .D(n447), .CPN(Clk_r), .Q(Packet_Out[3]) );
  FDN1QM1P \Queue_reg[1][2]  ( .D(n414), .CPN(Clk_r), .Q(\Queue[1][2] ) );
  FDN1QM1P \Queue_reg[0][2]  ( .D(n446), .CPN(Clk_r), .Q(Packet_Out[2]) );
  FDN1QM1P \Queue_reg[1][1]  ( .D(n413), .CPN(Clk_r), .Q(\Queue[1][1] ) );
  FDN1QM1P \Queue_reg[0][1]  ( .D(n445), .CPN(Clk_r), .Q(Packet_Out[1]) );
  FDN1QM1P \Queue_reg[1][0]  ( .D(n412), .CPN(Clk_r), .Q(\Queue[1][0] ) );
  FDN1QM1P \Queue_reg[0][0]  ( .D(n444), .CPN(Clk_r), .Q(Packet_Out[0]) );
  FDN2QM1P Empty_reg ( .D(n541), .CPN(Clk_r), .CD(Rst_n), .Q(n540) );
  FDN2QM3P Full_reg ( .D(n411), .CPN(Clk_r), .CD(Rst_n), .Q(Full) );
  N1M2P U3 ( .A(n61), .Z(n93) );
  OAI211M4P U4 ( .A(n99), .B(n136), .C(Write_Ack), .D(n23), .Z(n58) );
  BUFM1P U5 ( .A(n134), .Z(n9) );
  BUFM1P U6 ( .A(n207), .Z(n11) );
  BUFM2P U7 ( .A(n134), .Z(n8) );
  BUFM2P U8 ( .A(n207), .Z(n10) );
  NR3M1P U9 ( .A(Count[0]), .B(Count[2]), .C(n21), .Z(n99) );
  NR3M1P U10 ( .A(Count[2]), .B(n22), .C(n21), .Z(n139) );
  N1M1P U11 ( .A(n17), .Z(n138) );
  NR2I1M8P U12 ( .A(n60), .B(n20), .Z(n19) );
  OAI211M4P U13 ( .A(n99), .B(n138), .C(Rst_n), .D(n137), .Z(n132) );
  OAI21M8P U14 ( .A(Write_Ack), .B(Read_Ack), .C(n23), .Z(n55) );
  OAI211M4P U15 ( .A(n375), .B(n136), .C(Write_Ack), .D(n61), .Z(n96) );
  BUFM2P U16 ( .A(n56), .Z(n4) );
  BUFM2P U17 ( .A(n204), .Z(n5) );
  BUFM2P U18 ( .A(n94), .Z(n3) );
  NR2M2P U19 ( .A(Read_Ack), .B(n98), .Z(n17) );
  N1M1P U20 ( .A(Write_Ack), .Z(n98) );
  NR3M1P U21 ( .A(n22), .B(Count[2]), .C(Count[1]), .Z(n375) );
  ND2M1P U22 ( .A(n17), .B(Rst_n), .Z(n60) );
  NR3M1P U23 ( .A(Count[0]), .B(Count[1]), .C(n18), .Z(n141) );
  N1M1P U24 ( .A(Count[0]), .Z(n22) );
  N1M1P U25 ( .A(Count[1]), .Z(n21) );
  OAI211M4P U26 ( .A(n139), .B(n138), .C(Rst_n), .D(n137), .Z(n205) );
  N1M1P U27 ( .A(Count[2]), .Z(n18) );
  N1M1P U28 ( .A(n141), .Z(n20) );
  AOI21M1P U29 ( .A(Write_Ack), .B(n99), .C(n140), .Z(n56) );
  N1M1P U30 ( .A(Read_Ack), .Z(n136) );
  N1M1P U31 ( .A(Packet_In[0]), .Z(n143) );
  N1M1P U32 ( .A(Packet_In[2]), .Z(n147) );
  N1M1P U33 ( .A(Packet_In[4]), .Z(n151) );
  N1M1P U34 ( .A(Packet_In[3]), .Z(n149) );
  N1M1P U35 ( .A(Packet_In[5]), .Z(n153) );
  N1M1P U36 ( .A(Packet_In[9]), .Z(n161) );
  N1M1P U37 ( .A(Packet_In[8]), .Z(n159) );
  N1M1P U38 ( .A(Packet_In[11]), .Z(n165) );
  N1M1P U39 ( .A(Packet_In[30]), .Z(n203) );
  N1M1P U40 ( .A(Packet_In[7]), .Z(n157) );
  N1M1P U41 ( .A(Packet_In[10]), .Z(n163) );
  N1M1P U42 ( .A(Packet_In[6]), .Z(n155) );
  N1M1P U43 ( .A(Packet_In[22]), .Z(n187) );
  N1M1P U44 ( .A(Packet_In[24]), .Z(n191) );
  N1M1P U45 ( .A(Packet_In[25]), .Z(n193) );
  N1M1P U46 ( .A(Packet_In[26]), .Z(n195) );
  N1M1P U47 ( .A(Packet_In[27]), .Z(n197) );
  N1M1P U48 ( .A(Packet_In[31]), .Z(n208) );
  N1M1P U49 ( .A(Packet_In[29]), .Z(n201) );
  N1M1P U50 ( .A(Packet_In[28]), .Z(n199) );
  N1M1P U51 ( .A(Packet_In[1]), .Z(n145) );
  N1M1P U52 ( .A(Packet_In[23]), .Z(n189) );
  N1M1P U53 ( .A(Packet_In[21]), .Z(n185) );
  N1M1P U54 ( .A(Packet_In[20]), .Z(n183) );
  N1M1P U55 ( .A(Packet_In[13]), .Z(n169) );
  N1M1P U56 ( .A(Packet_In[14]), .Z(n171) );
  N1M1P U57 ( .A(Packet_In[12]), .Z(n167) );
  N1M1P U58 ( .A(Packet_In[15]), .Z(n173) );
  N1M1P U59 ( .A(Packet_In[18]), .Z(n179) );
  N1M1P U60 ( .A(Packet_In[17]), .Z(n177) );
  N1M1P U61 ( .A(Packet_In[16]), .Z(n175) );
  ND2M1P U62 ( .A(Read_Ack), .B(Rst_n), .Z(n140) );
  N1M1P U63 ( .A(Packet_In[19]), .Z(n181) );
  OAI211M1P U64 ( .A(n141), .B(n136), .C(Write_Ack), .D(n135), .Z(n207) );
  AOI21M6P U65 ( .A(Write_Ack), .B(n139), .C(n140), .Z(n131) );
  OAI211M1P U66 ( .A(n139), .B(n136), .C(Write_Ack), .D(n97), .Z(n134) );
  BUFM1P U67 ( .A(Full), .Z(n7) );
  NR2M1P U68 ( .A(n136), .B(Write_Ack), .Z(n374) );
  NR2M1P U69 ( .A(n17), .B(n374), .Z(n373) );
  AOI22CDNM1P U70 ( .A(n373), .B(n22), .C(n373), .D(n22), .Z(n376) );
  N1M1P U71 ( .A(n374), .Z(n16) );
  AOI22M1P U72 ( .A(Count[0]), .B(n138), .C(n16), .D(n22), .Z(n13) );
  AOI22CDNM1P U73 ( .A(Count[1]), .B(n13), .C(n13), .D(Count[1]), .Z(n377) );
  ND3M1P U74 ( .A(n22), .B(n18), .C(n21), .Z(n59) );
  ND2M1P U75 ( .A(Count[1]), .B(Read_Ack), .Z(n12) );
  OAI211M1P U76 ( .A(Count[1]), .B(n138), .C(n13), .D(n12), .Z(n14) );
  AOI22M1P U77 ( .A(n17), .B(n139), .C(Count[2]), .D(n14), .Z(n15) );
  OAI21M1P U78 ( .A(n59), .B(n16), .C(n15), .Z(n378) );
  AOI22CDNM1P U79 ( .A(n19), .B(n208), .C(\Queue[4][31] ), .D(n19), .Z(n379)
         );
  AOI22CDNM1P U80 ( .A(n19), .B(n143), .C(\Queue[4][0] ), .D(n19), .Z(n380) );
  AOI22CDNM1P U81 ( .A(n19), .B(n145), .C(\Queue[4][1] ), .D(n19), .Z(n381) );
  AOI22CDNM1P U82 ( .A(n19), .B(n147), .C(\Queue[4][2] ), .D(n19), .Z(n382) );
  AOI22CDNM1P U83 ( .A(n19), .B(n149), .C(\Queue[4][3] ), .D(n19), .Z(n383) );
  AOI22CDNM1P U84 ( .A(n19), .B(n151), .C(\Queue[4][4] ), .D(n19), .Z(n384) );
  AOI22CDNM1P U85 ( .A(n19), .B(n153), .C(\Queue[4][5] ), .D(n19), .Z(n385) );
  AOI22CDNM1P U86 ( .A(n19), .B(n155), .C(\Queue[4][6] ), .D(n19), .Z(n386) );
  AOI22CDNM1P U87 ( .A(n19), .B(n157), .C(\Queue[4][7] ), .D(n19), .Z(n387) );
  AOI22CDNM1P U88 ( .A(n19), .B(n159), .C(\Queue[4][8] ), .D(n19), .Z(n388) );
  AOI22CDNM1P U89 ( .A(n19), .B(n161), .C(\Queue[4][9] ), .D(n19), .Z(n389) );
  AOI22CDNM1P U90 ( .A(n19), .B(n163), .C(\Queue[4][10] ), .D(n19), .Z(n390)
         );
  AOI22CDNM1P U91 ( .A(n19), .B(n165), .C(\Queue[4][11] ), .D(n19), .Z(n391)
         );
  AOI22CDNM1P U92 ( .A(n19), .B(n167), .C(\Queue[4][12] ), .D(n19), .Z(n392)
         );
  AOI22CDNM1P U93 ( .A(n19), .B(n169), .C(\Queue[4][13] ), .D(n19), .Z(n393)
         );
  AOI22CDNM1P U94 ( .A(n19), .B(n171), .C(\Queue[4][14] ), .D(n19), .Z(n394)
         );
  AOI22CDNM1P U95 ( .A(n19), .B(n173), .C(\Queue[4][15] ), .D(n19), .Z(n395)
         );
  AOI22CDNM1P U96 ( .A(n19), .B(n175), .C(\Queue[4][16] ), .D(n19), .Z(n396)
         );
  AOI22CDNM1P U97 ( .A(n19), .B(n177), .C(\Queue[4][17] ), .D(n19), .Z(n397)
         );
  AOI22CDNM1P U98 ( .A(n19), .B(n179), .C(\Queue[4][18] ), .D(n19), .Z(n398)
         );
  AOI22CDNM1P U99 ( .A(n19), .B(n181), .C(\Queue[4][19] ), .D(n19), .Z(n399)
         );
  AOI22CDNM1P U100 ( .A(n19), .B(n183), .C(\Queue[4][20] ), .D(n19), .Z(n400)
         );
  AOI22CDNM1P U101 ( .A(n19), .B(n185), .C(\Queue[4][21] ), .D(n19), .Z(n401)
         );
  AOI22CDNM1P U102 ( .A(n19), .B(n187), .C(\Queue[4][22] ), .D(n19), .Z(n402)
         );
  AOI22CDNM1P U103 ( .A(n19), .B(n189), .C(\Queue[4][23] ), .D(n19), .Z(n403)
         );
  AOI22CDNM1P U104 ( .A(n19), .B(n191), .C(\Queue[4][24] ), .D(n19), .Z(n404)
         );
  AOI22CDNM1P U105 ( .A(n19), .B(n193), .C(\Queue[4][25] ), .D(n19), .Z(n405)
         );
  AOI22CDNM1P U106 ( .A(n19), .B(n195), .C(\Queue[4][26] ), .D(n19), .Z(n406)
         );
  AOI22CDNM1P U107 ( .A(n19), .B(n197), .C(\Queue[4][27] ), .D(n19), .Z(n407)
         );
  AOI22CDNM1P U108 ( .A(n19), .B(n199), .C(\Queue[4][28] ), .D(n19), .Z(n408)
         );
  AOI22CDNM1P U109 ( .A(n19), .B(n201), .C(\Queue[4][29] ), .D(n19), .Z(n409)
         );
  AOI22CDNM1P U110 ( .A(n19), .B(n203), .C(\Queue[4][30] ), .D(n19), .Z(n410)
         );
  OAI22CDNM1P U111 ( .A(n138), .B(n20), .C(n373), .D(n7), .Z(n411) );
  OA21M1P U112 ( .A(n138), .B(n375), .C(Rst_n), .Z(n23) );
  AOI22M1P U113 ( .A(\Queue[2][0] ), .B(n4), .C(\Queue[1][0] ), .D(n55), .Z(
        n24) );
  OAI21M1P U114 ( .A(n143), .B(n58), .C(n24), .Z(n412) );
  AOI22M1P U115 ( .A(n4), .B(\Queue[2][1] ), .C(\Queue[1][1] ), .D(n55), .Z(
        n25) );
  OAI21M1P U116 ( .A(n58), .B(n145), .C(n25), .Z(n413) );
  AOI22M1P U117 ( .A(n4), .B(\Queue[2][2] ), .C(\Queue[1][2] ), .D(n55), .Z(
        n26) );
  OAI21M1P U118 ( .A(n58), .B(n147), .C(n26), .Z(n414) );
  AOI22M1P U119 ( .A(n4), .B(\Queue[2][3] ), .C(\Queue[1][3] ), .D(n55), .Z(
        n27) );
  OAI21M1P U120 ( .A(n58), .B(n149), .C(n27), .Z(n415) );
  AOI22M1P U121 ( .A(n4), .B(\Queue[2][4] ), .C(\Queue[1][4] ), .D(n55), .Z(
        n28) );
  OAI21M1P U122 ( .A(n58), .B(n151), .C(n28), .Z(n416) );
  AOI22M1P U123 ( .A(n4), .B(\Queue[2][5] ), .C(\Queue[1][5] ), .D(n55), .Z(
        n29) );
  OAI21M1P U124 ( .A(n58), .B(n153), .C(n29), .Z(n417) );
  AOI22M1P U125 ( .A(n4), .B(\Queue[2][6] ), .C(\Queue[1][6] ), .D(n55), .Z(
        n30) );
  OAI21M1P U126 ( .A(n58), .B(n155), .C(n30), .Z(n418) );
  AOI22M1P U127 ( .A(n4), .B(\Queue[2][7] ), .C(\Queue[1][7] ), .D(n55), .Z(
        n31) );
  OAI21M1P U128 ( .A(n58), .B(n157), .C(n31), .Z(n419) );
  AOI22M1P U129 ( .A(n4), .B(\Queue[2][8] ), .C(\Queue[1][8] ), .D(n55), .Z(
        n32) );
  OAI21M1P U130 ( .A(n58), .B(n159), .C(n32), .Z(n420) );
  AOI22M1P U131 ( .A(n4), .B(\Queue[2][9] ), .C(\Queue[1][9] ), .D(n55), .Z(
        n33) );
  OAI21M1P U132 ( .A(n58), .B(n161), .C(n33), .Z(n421) );
  AOI22M1P U133 ( .A(n4), .B(\Queue[2][10] ), .C(\Queue[1][10] ), .D(n55), .Z(
        n34) );
  OAI21M1P U134 ( .A(n58), .B(n163), .C(n34), .Z(n422) );
  AOI22M1P U135 ( .A(n4), .B(\Queue[2][11] ), .C(\Queue[1][11] ), .D(n55), .Z(
        n35) );
  OAI21M1P U136 ( .A(n58), .B(n165), .C(n35), .Z(n423) );
  AOI22M1P U137 ( .A(n4), .B(\Queue[2][12] ), .C(\Queue[1][12] ), .D(n55), .Z(
        n36) );
  OAI21M1P U138 ( .A(n58), .B(n167), .C(n36), .Z(n424) );
  AOI22M1P U139 ( .A(n4), .B(\Queue[2][13] ), .C(\Queue[1][13] ), .D(n55), .Z(
        n37) );
  OAI21M1P U140 ( .A(n58), .B(n169), .C(n37), .Z(n425) );
  AOI22M1P U141 ( .A(n4), .B(\Queue[2][14] ), .C(\Queue[1][14] ), .D(n55), .Z(
        n38) );
  OAI21M1P U142 ( .A(n58), .B(n171), .C(n38), .Z(n426) );
  AOI22M1P U143 ( .A(n4), .B(\Queue[2][15] ), .C(\Queue[1][15] ), .D(n55), .Z(
        n39) );
  OAI21M1P U144 ( .A(n58), .B(n173), .C(n39), .Z(n427) );
  AOI22M1P U145 ( .A(n4), .B(\Queue[2][16] ), .C(\Queue[1][16] ), .D(n55), .Z(
        n40) );
  OAI21M1P U146 ( .A(n58), .B(n175), .C(n40), .Z(n428) );
  AOI22M1P U147 ( .A(n4), .B(\Queue[2][17] ), .C(\Queue[1][17] ), .D(n55), .Z(
        n41) );
  OAI21M1P U148 ( .A(n58), .B(n177), .C(n41), .Z(n429) );
  AOI22M1P U149 ( .A(n4), .B(\Queue[2][18] ), .C(\Queue[1][18] ), .D(n55), .Z(
        n42) );
  OAI21M1P U150 ( .A(n58), .B(n179), .C(n42), .Z(n430) );
  AOI22M1P U151 ( .A(n4), .B(\Queue[2][19] ), .C(\Queue[1][19] ), .D(n55), .Z(
        n43) );
  OAI21M1P U152 ( .A(n58), .B(n181), .C(n43), .Z(n431) );
  AOI22M1P U153 ( .A(n4), .B(\Queue[2][20] ), .C(\Queue[1][20] ), .D(n55), .Z(
        n44) );
  OAI21M1P U154 ( .A(n58), .B(n183), .C(n44), .Z(n432) );
  AOI22M1P U155 ( .A(n4), .B(\Queue[2][21] ), .C(\Queue[1][21] ), .D(n55), .Z(
        n45) );
  OAI21M1P U156 ( .A(n58), .B(n185), .C(n45), .Z(n433) );
  AOI22M1P U157 ( .A(n4), .B(\Queue[2][22] ), .C(\Queue[1][22] ), .D(n55), .Z(
        n46) );
  OAI21M1P U158 ( .A(n58), .B(n187), .C(n46), .Z(n434) );
  AOI22M1P U159 ( .A(n4), .B(\Queue[2][23] ), .C(\Queue[1][23] ), .D(n55), .Z(
        n47) );
  OAI21M1P U160 ( .A(n58), .B(n189), .C(n47), .Z(n435) );
  AOI22M1P U161 ( .A(n4), .B(\Queue[2][24] ), .C(\Queue[1][24] ), .D(n55), .Z(
        n48) );
  OAI21M1P U162 ( .A(n58), .B(n191), .C(n48), .Z(n436) );
  AOI22M1P U163 ( .A(n56), .B(\Queue[2][25] ), .C(\Queue[1][25] ), .D(n55), 
        .Z(n49) );
  OAI21M1P U164 ( .A(n58), .B(n193), .C(n49), .Z(n437) );
  AOI22M1P U165 ( .A(n56), .B(\Queue[2][26] ), .C(\Queue[1][26] ), .D(n55), 
        .Z(n50) );
  OAI21M1P U166 ( .A(n58), .B(n195), .C(n50), .Z(n438) );
  AOI22M1P U167 ( .A(n56), .B(\Queue[2][27] ), .C(\Queue[1][27] ), .D(n55), 
        .Z(n51) );
  OAI21M1P U168 ( .A(n58), .B(n197), .C(n51), .Z(n439) );
  AOI22M1P U169 ( .A(n56), .B(\Queue[2][28] ), .C(\Queue[1][28] ), .D(n55), 
        .Z(n52) );
  OAI21M1P U170 ( .A(n58), .B(n199), .C(n52), .Z(n440) );
  AOI22M1P U171 ( .A(n56), .B(\Queue[2][29] ), .C(\Queue[1][29] ), .D(n55), 
        .Z(n53) );
  OAI21M1P U172 ( .A(n58), .B(n201), .C(n53), .Z(n441) );
  AOI22M1P U173 ( .A(n56), .B(\Queue[2][30] ), .C(\Queue[1][30] ), .D(n55), 
        .Z(n54) );
  OAI21M1P U174 ( .A(n58), .B(n203), .C(n54), .Z(n442) );
  AOI22M1P U175 ( .A(n56), .B(\Queue[2][31] ), .C(\Queue[1][31] ), .D(n55), 
        .Z(n57) );
  OAI21M1P U176 ( .A(n58), .B(n208), .C(n57), .Z(n443) );
  OAI21M1P U177 ( .A(n60), .B(n59), .C(n140), .Z(n61) );
  AOI21M1P U178 ( .A(Write_Ack), .B(n375), .C(n140), .Z(n94) );
  AOI22M1P U179 ( .A(\Queue[1][0] ), .B(n3), .C(n93), .D(Packet_Out[0]), .Z(
        n62) );
  OAI21M1P U180 ( .A(n143), .B(n96), .C(n62), .Z(n444) );
  AOI22M1P U181 ( .A(\Queue[1][1] ), .B(n3), .C(n93), .D(Packet_Out[1]), .Z(
        n63) );
  OAI21M1P U182 ( .A(n145), .B(n96), .C(n63), .Z(n445) );
  AOI22M1P U183 ( .A(\Queue[1][2] ), .B(n3), .C(n93), .D(Packet_Out[2]), .Z(
        n64) );
  OAI21M1P U184 ( .A(n147), .B(n96), .C(n64), .Z(n446) );
  AOI22M1P U185 ( .A(\Queue[1][3] ), .B(n3), .C(n93), .D(Packet_Out[3]), .Z(
        n65) );
  OAI21M1P U186 ( .A(n149), .B(n96), .C(n65), .Z(n447) );
  AOI22M1P U187 ( .A(\Queue[1][4] ), .B(n3), .C(n93), .D(Packet_Out[4]), .Z(
        n66) );
  OAI21M1P U188 ( .A(n151), .B(n96), .C(n66), .Z(n448) );
  AOI22M1P U189 ( .A(\Queue[1][5] ), .B(n3), .C(n93), .D(Packet_Out[5]), .Z(
        n67) );
  OAI21M1P U190 ( .A(n153), .B(n96), .C(n67), .Z(n449) );
  AOI22M1P U191 ( .A(\Queue[1][6] ), .B(n3), .C(n93), .D(Packet_Out[6]), .Z(
        n68) );
  OAI21M1P U192 ( .A(n155), .B(n96), .C(n68), .Z(n450) );
  AOI22M1P U193 ( .A(\Queue[1][7] ), .B(n3), .C(n93), .D(Packet_Out[7]), .Z(
        n69) );
  OAI21M1P U194 ( .A(n157), .B(n96), .C(n69), .Z(n451) );
  AOI22M1P U195 ( .A(\Queue[1][8] ), .B(n3), .C(n93), .D(Packet_Out[8]), .Z(
        n70) );
  OAI21M1P U196 ( .A(n159), .B(n96), .C(n70), .Z(n452) );
  AOI22M1P U197 ( .A(\Queue[1][9] ), .B(n3), .C(n93), .D(Packet_Out[9]), .Z(
        n71) );
  OAI21M1P U198 ( .A(n161), .B(n96), .C(n71), .Z(n453) );
  AOI22M1P U199 ( .A(\Queue[1][10] ), .B(n3), .C(n93), .D(Packet_Out[10]), .Z(
        n72) );
  OAI21M1P U200 ( .A(n163), .B(n96), .C(n72), .Z(n454) );
  AOI22M1P U201 ( .A(\Queue[1][11] ), .B(n3), .C(n93), .D(Packet_Out[11]), .Z(
        n73) );
  OAI21M1P U202 ( .A(n165), .B(n96), .C(n73), .Z(n455) );
  AOI22M1P U203 ( .A(\Queue[1][12] ), .B(n3), .C(n93), .D(Packet_Out[12]), .Z(
        n74) );
  OAI21M1P U204 ( .A(n167), .B(n96), .C(n74), .Z(n456) );
  AOI22M1P U205 ( .A(\Queue[1][13] ), .B(n3), .C(n93), .D(Packet_Out[13]), .Z(
        n75) );
  OAI21M1P U206 ( .A(n169), .B(n96), .C(n75), .Z(n457) );
  AOI22M1P U207 ( .A(\Queue[1][14] ), .B(n3), .C(n93), .D(Packet_Out[14]), .Z(
        n76) );
  OAI21M1P U208 ( .A(n171), .B(n96), .C(n76), .Z(n458) );
  AOI22M1P U209 ( .A(\Queue[1][15] ), .B(n3), .C(n93), .D(Packet_Out[15]), .Z(
        n77) );
  OAI21M1P U210 ( .A(n173), .B(n96), .C(n77), .Z(n459) );
  AOI22M1P U211 ( .A(\Queue[1][16] ), .B(n3), .C(n93), .D(Packet_Out[16]), .Z(
        n78) );
  OAI21M1P U212 ( .A(n175), .B(n96), .C(n78), .Z(n460) );
  AOI22M1P U213 ( .A(\Queue[1][17] ), .B(n3), .C(n93), .D(Packet_Out[17]), .Z(
        n79) );
  OAI21M1P U214 ( .A(n177), .B(n96), .C(n79), .Z(n461) );
  AOI22M1P U215 ( .A(\Queue[1][18] ), .B(n3), .C(n93), .D(Packet_Out[18]), .Z(
        n80) );
  OAI21M1P U216 ( .A(n179), .B(n96), .C(n80), .Z(n462) );
  AOI22M1P U217 ( .A(\Queue[1][19] ), .B(n3), .C(n93), .D(Packet_Out[19]), .Z(
        n81) );
  OAI21M1P U218 ( .A(n181), .B(n96), .C(n81), .Z(n463) );
  AOI22M1P U219 ( .A(\Queue[1][20] ), .B(n3), .C(n93), .D(Packet_Out[20]), .Z(
        n82) );
  OAI21M1P U220 ( .A(n183), .B(n96), .C(n82), .Z(n464) );
  AOI22M1P U221 ( .A(\Queue[1][21] ), .B(n3), .C(n93), .D(Packet_Out[21]), .Z(
        n83) );
  OAI21M1P U222 ( .A(n185), .B(n96), .C(n83), .Z(n465) );
  AOI22M1P U223 ( .A(\Queue[1][22] ), .B(n3), .C(n93), .D(Packet_Out[22]), .Z(
        n84) );
  OAI21M1P U224 ( .A(n187), .B(n96), .C(n84), .Z(n466) );
  AOI22M1P U225 ( .A(\Queue[1][23] ), .B(n3), .C(n93), .D(Packet_Out[23]), .Z(
        n85) );
  OAI21M1P U226 ( .A(n189), .B(n96), .C(n85), .Z(n467) );
  AOI22M1P U227 ( .A(\Queue[1][24] ), .B(n3), .C(n93), .D(Packet_Out[24]), .Z(
        n86) );
  OAI21M1P U228 ( .A(n191), .B(n96), .C(n86), .Z(n468) );
  AOI22M1P U229 ( .A(\Queue[1][25] ), .B(n3), .C(n93), .D(Packet_Out[25]), .Z(
        n87) );
  OAI21M1P U230 ( .A(n193), .B(n96), .C(n87), .Z(n469) );
  AOI22M1P U231 ( .A(\Queue[1][26] ), .B(n3), .C(n93), .D(Packet_Out[26]), .Z(
        n88) );
  OAI21M1P U232 ( .A(n195), .B(n96), .C(n88), .Z(n470) );
  AOI22M1P U233 ( .A(\Queue[1][27] ), .B(n3), .C(n93), .D(Packet_Out[27]), .Z(
        n89) );
  OAI21M1P U234 ( .A(n197), .B(n96), .C(n89), .Z(n471) );
  AOI22M1P U235 ( .A(\Queue[1][28] ), .B(n3), .C(n93), .D(Packet_Out[28]), .Z(
        n90) );
  OAI21M1P U236 ( .A(n199), .B(n96), .C(n90), .Z(n472) );
  AOI22M1P U237 ( .A(\Queue[1][29] ), .B(n3), .C(n93), .D(Packet_Out[29]), .Z(
        n91) );
  OAI21M1P U238 ( .A(n201), .B(n96), .C(n91), .Z(n473) );
  AOI22M1P U239 ( .A(\Queue[1][30] ), .B(n94), .C(n93), .D(Packet_Out[30]), 
        .Z(n92) );
  OAI21M1P U240 ( .A(n203), .B(n96), .C(n92), .Z(n474) );
  AOI22M1P U241 ( .A(\Queue[1][31] ), .B(n94), .C(n93), .D(Packet_Out[31]), 
        .Z(n95) );
  OAI21M1P U242 ( .A(n208), .B(n96), .C(n95), .Z(n475) );
  OA21M1P U243 ( .A(n138), .B(n99), .C(Rst_n), .Z(n97) );
  ND2M1P U244 ( .A(n98), .B(n136), .Z(n137) );
  AOI22M1P U245 ( .A(\Queue[2][0] ), .B(n132), .C(\Queue[3][0] ), .D(n131), 
        .Z(n100) );
  OAI21M1P U246 ( .A(n143), .B(n8), .C(n100), .Z(n476) );
  AOI22M1P U247 ( .A(\Queue[2][1] ), .B(n132), .C(n131), .D(\Queue[3][1] ), 
        .Z(n101) );
  OAI21M1P U248 ( .A(n145), .B(n8), .C(n101), .Z(n477) );
  AOI22M1P U249 ( .A(\Queue[2][2] ), .B(n132), .C(n131), .D(\Queue[3][2] ), 
        .Z(n102) );
  OAI21M1P U250 ( .A(n147), .B(n8), .C(n102), .Z(n478) );
  AOI22M1P U251 ( .A(\Queue[2][3] ), .B(n132), .C(n131), .D(\Queue[3][3] ), 
        .Z(n103) );
  OAI21M1P U252 ( .A(n149), .B(n8), .C(n103), .Z(n479) );
  AOI22M1P U253 ( .A(\Queue[2][4] ), .B(n132), .C(n131), .D(\Queue[3][4] ), 
        .Z(n104) );
  OAI21M1P U254 ( .A(n151), .B(n8), .C(n104), .Z(n480) );
  AOI22M1P U255 ( .A(\Queue[2][5] ), .B(n132), .C(n131), .D(\Queue[3][5] ), 
        .Z(n105) );
  OAI21M1P U256 ( .A(n153), .B(n8), .C(n105), .Z(n481) );
  AOI22M1P U257 ( .A(\Queue[2][6] ), .B(n132), .C(n131), .D(\Queue[3][6] ), 
        .Z(n106) );
  OAI21M1P U258 ( .A(n155), .B(n8), .C(n106), .Z(n482) );
  AOI22M1P U259 ( .A(\Queue[2][7] ), .B(n132), .C(n131), .D(\Queue[3][7] ), 
        .Z(n107) );
  OAI21M1P U260 ( .A(n157), .B(n8), .C(n107), .Z(n483) );
  AOI22M1P U261 ( .A(\Queue[2][8] ), .B(n132), .C(n131), .D(\Queue[3][8] ), 
        .Z(n108) );
  OAI21M1P U262 ( .A(n159), .B(n8), .C(n108), .Z(n484) );
  AOI22M1P U263 ( .A(\Queue[2][9] ), .B(n132), .C(n131), .D(\Queue[3][9] ), 
        .Z(n109) );
  OAI21M1P U264 ( .A(n161), .B(n8), .C(n109), .Z(n485) );
  AOI22M1P U265 ( .A(\Queue[2][10] ), .B(n132), .C(n131), .D(\Queue[3][10] ), 
        .Z(n110) );
  OAI21M1P U266 ( .A(n163), .B(n8), .C(n110), .Z(n486) );
  AOI22M1P U267 ( .A(\Queue[2][11] ), .B(n132), .C(n131), .D(\Queue[3][11] ), 
        .Z(n111) );
  OAI21M1P U268 ( .A(n165), .B(n8), .C(n111), .Z(n487) );
  AOI22M1P U269 ( .A(\Queue[2][12] ), .B(n132), .C(n131), .D(\Queue[3][12] ), 
        .Z(n112) );
  OAI21M1P U270 ( .A(n167), .B(n8), .C(n112), .Z(n488) );
  AOI22M1P U271 ( .A(\Queue[2][13] ), .B(n132), .C(n131), .D(\Queue[3][13] ), 
        .Z(n113) );
  OAI21M1P U272 ( .A(n169), .B(n8), .C(n113), .Z(n489) );
  AOI22M1P U273 ( .A(\Queue[2][14] ), .B(n132), .C(n131), .D(\Queue[3][14] ), 
        .Z(n114) );
  OAI21M1P U274 ( .A(n171), .B(n8), .C(n114), .Z(n490) );
  AOI22M1P U275 ( .A(\Queue[2][15] ), .B(n132), .C(n131), .D(\Queue[3][15] ), 
        .Z(n115) );
  OAI21M1P U276 ( .A(n173), .B(n8), .C(n115), .Z(n491) );
  AOI22M1P U277 ( .A(\Queue[2][16] ), .B(n132), .C(n131), .D(\Queue[3][16] ), 
        .Z(n116) );
  OAI21M1P U278 ( .A(n175), .B(n8), .C(n116), .Z(n492) );
  AOI22M1P U279 ( .A(\Queue[2][17] ), .B(n132), .C(n131), .D(\Queue[3][17] ), 
        .Z(n117) );
  OAI21M1P U280 ( .A(n177), .B(n8), .C(n117), .Z(n493) );
  AOI22M1P U281 ( .A(\Queue[2][18] ), .B(n132), .C(n131), .D(\Queue[3][18] ), 
        .Z(n118) );
  OAI21M1P U282 ( .A(n179), .B(n8), .C(n118), .Z(n494) );
  AOI22M1P U283 ( .A(\Queue[2][19] ), .B(n132), .C(n131), .D(\Queue[3][19] ), 
        .Z(n119) );
  OAI21M1P U284 ( .A(n181), .B(n8), .C(n119), .Z(n495) );
  AOI22M1P U285 ( .A(\Queue[2][20] ), .B(n132), .C(n131), .D(\Queue[3][20] ), 
        .Z(n120) );
  OAI21M1P U286 ( .A(n183), .B(n9), .C(n120), .Z(n496) );
  AOI22M1P U287 ( .A(\Queue[2][21] ), .B(n132), .C(n131), .D(\Queue[3][21] ), 
        .Z(n121) );
  OAI21M1P U288 ( .A(n185), .B(n9), .C(n121), .Z(n497) );
  AOI22M1P U289 ( .A(\Queue[2][22] ), .B(n132), .C(n131), .D(\Queue[3][22] ), 
        .Z(n122) );
  OAI21M1P U290 ( .A(n187), .B(n9), .C(n122), .Z(n498) );
  AOI22M1P U291 ( .A(\Queue[2][23] ), .B(n132), .C(n131), .D(\Queue[3][23] ), 
        .Z(n123) );
  OAI21M1P U292 ( .A(n189), .B(n9), .C(n123), .Z(n499) );
  AOI22M1P U293 ( .A(\Queue[2][24] ), .B(n132), .C(n131), .D(\Queue[3][24] ), 
        .Z(n124) );
  OAI21M1P U294 ( .A(n191), .B(n9), .C(n124), .Z(n500) );
  AOI22M1P U295 ( .A(\Queue[2][25] ), .B(n132), .C(n131), .D(\Queue[3][25] ), 
        .Z(n125) );
  OAI21M1P U296 ( .A(n193), .B(n9), .C(n125), .Z(n501) );
  AOI22M1P U297 ( .A(\Queue[2][26] ), .B(n132), .C(n131), .D(\Queue[3][26] ), 
        .Z(n126) );
  OAI21M1P U298 ( .A(n195), .B(n9), .C(n126), .Z(n502) );
  AOI22M1P U299 ( .A(\Queue[2][27] ), .B(n132), .C(n131), .D(\Queue[3][27] ), 
        .Z(n127) );
  OAI21M1P U300 ( .A(n197), .B(n9), .C(n127), .Z(n503) );
  AOI22M1P U301 ( .A(\Queue[2][28] ), .B(n132), .C(n131), .D(\Queue[3][28] ), 
        .Z(n128) );
  OAI21M1P U302 ( .A(n199), .B(n9), .C(n128), .Z(n504) );
  AOI22M1P U303 ( .A(\Queue[2][29] ), .B(n132), .C(n131), .D(\Queue[3][29] ), 
        .Z(n129) );
  OAI21M1P U304 ( .A(n201), .B(n9), .C(n129), .Z(n505) );
  AOI22M1P U305 ( .A(\Queue[2][30] ), .B(n132), .C(n131), .D(\Queue[3][30] ), 
        .Z(n130) );
  OAI21M1P U306 ( .A(n203), .B(n8), .C(n130), .Z(n506) );
  AOI22M1P U307 ( .A(\Queue[2][31] ), .B(n132), .C(n131), .D(\Queue[3][31] ), 
        .Z(n133) );
  OAI21M1P U308 ( .A(n208), .B(n9), .C(n133), .Z(n507) );
  OA21M1P U309 ( .A(n138), .B(n139), .C(Rst_n), .Z(n135) );
  AOI21M1P U310 ( .A(Write_Ack), .B(n141), .C(n140), .Z(n204) );
  AOI22M1P U311 ( .A(\Queue[3][0] ), .B(n205), .C(n5), .D(\Queue[4][0] ), .Z(
        n142) );
  OAI21M1P U312 ( .A(n143), .B(n10), .C(n142), .Z(n508) );
  AOI22M1P U313 ( .A(\Queue[3][1] ), .B(n205), .C(n5), .D(\Queue[4][1] ), .Z(
        n144) );
  OAI21M1P U314 ( .A(n145), .B(n10), .C(n144), .Z(n509) );
  AOI22M1P U315 ( .A(\Queue[3][2] ), .B(n205), .C(n5), .D(\Queue[4][2] ), .Z(
        n146) );
  OAI21M1P U316 ( .A(n147), .B(n10), .C(n146), .Z(n510) );
  AOI22M1P U317 ( .A(\Queue[3][3] ), .B(n205), .C(n5), .D(\Queue[4][3] ), .Z(
        n148) );
  OAI21M1P U318 ( .A(n149), .B(n10), .C(n148), .Z(n511) );
  AOI22M1P U319 ( .A(\Queue[3][4] ), .B(n205), .C(n5), .D(\Queue[4][4] ), .Z(
        n150) );
  OAI21M1P U320 ( .A(n151), .B(n10), .C(n150), .Z(n512) );
  AOI22M1P U321 ( .A(\Queue[3][5] ), .B(n205), .C(n5), .D(\Queue[4][5] ), .Z(
        n152) );
  OAI21M1P U322 ( .A(n153), .B(n10), .C(n152), .Z(n513) );
  AOI22M1P U323 ( .A(\Queue[3][6] ), .B(n205), .C(n5), .D(\Queue[4][6] ), .Z(
        n154) );
  OAI21M1P U324 ( .A(n155), .B(n10), .C(n154), .Z(n514) );
  AOI22M1P U325 ( .A(\Queue[3][7] ), .B(n205), .C(n5), .D(\Queue[4][7] ), .Z(
        n156) );
  OAI21M1P U326 ( .A(n157), .B(n10), .C(n156), .Z(n515) );
  AOI22M1P U327 ( .A(\Queue[3][8] ), .B(n205), .C(n5), .D(\Queue[4][8] ), .Z(
        n158) );
  OAI21M1P U328 ( .A(n159), .B(n10), .C(n158), .Z(n516) );
  AOI22M1P U329 ( .A(\Queue[3][9] ), .B(n205), .C(n5), .D(\Queue[4][9] ), .Z(
        n160) );
  OAI21M1P U330 ( .A(n161), .B(n10), .C(n160), .Z(n517) );
  AOI22M1P U331 ( .A(\Queue[3][10] ), .B(n205), .C(n5), .D(\Queue[4][10] ), 
        .Z(n162) );
  OAI21M1P U332 ( .A(n163), .B(n10), .C(n162), .Z(n518) );
  AOI22M1P U333 ( .A(\Queue[3][11] ), .B(n205), .C(n5), .D(\Queue[4][11] ), 
        .Z(n164) );
  OAI21M1P U334 ( .A(n165), .B(n10), .C(n164), .Z(n519) );
  AOI22M1P U335 ( .A(\Queue[3][12] ), .B(n205), .C(n5), .D(\Queue[4][12] ), 
        .Z(n166) );
  OAI21M1P U336 ( .A(n167), .B(n10), .C(n166), .Z(n520) );
  AOI22M1P U337 ( .A(\Queue[3][13] ), .B(n205), .C(n5), .D(\Queue[4][13] ), 
        .Z(n168) );
  OAI21M1P U338 ( .A(n169), .B(n10), .C(n168), .Z(n521) );
  AOI22M1P U339 ( .A(\Queue[3][14] ), .B(n205), .C(n5), .D(\Queue[4][14] ), 
        .Z(n170) );
  OAI21M1P U340 ( .A(n171), .B(n10), .C(n170), .Z(n522) );
  AOI22M1P U341 ( .A(\Queue[3][15] ), .B(n205), .C(n5), .D(\Queue[4][15] ), 
        .Z(n172) );
  OAI21M1P U342 ( .A(n173), .B(n10), .C(n172), .Z(n523) );
  AOI22M1P U343 ( .A(\Queue[3][16] ), .B(n205), .C(n5), .D(\Queue[4][16] ), 
        .Z(n174) );
  OAI21M1P U344 ( .A(n175), .B(n10), .C(n174), .Z(n524) );
  AOI22M1P U345 ( .A(\Queue[3][17] ), .B(n205), .C(n5), .D(\Queue[4][17] ), 
        .Z(n176) );
  OAI21M1P U346 ( .A(n177), .B(n10), .C(n176), .Z(n525) );
  AOI22M1P U347 ( .A(\Queue[3][18] ), .B(n205), .C(n5), .D(\Queue[4][18] ), 
        .Z(n178) );
  OAI21M1P U348 ( .A(n179), .B(n10), .C(n178), .Z(n526) );
  AOI22M1P U349 ( .A(\Queue[3][19] ), .B(n205), .C(n5), .D(\Queue[4][19] ), 
        .Z(n180) );
  OAI21M1P U350 ( .A(n181), .B(n10), .C(n180), .Z(n527) );
  AOI22M1P U351 ( .A(\Queue[3][20] ), .B(n205), .C(n5), .D(\Queue[4][20] ), 
        .Z(n182) );
  OAI21M1P U352 ( .A(n183), .B(n11), .C(n182), .Z(n528) );
  AOI22M1P U353 ( .A(\Queue[3][21] ), .B(n205), .C(n5), .D(\Queue[4][21] ), 
        .Z(n184) );
  OAI21M1P U354 ( .A(n185), .B(n11), .C(n184), .Z(n529) );
  AOI22M1P U355 ( .A(\Queue[3][22] ), .B(n205), .C(n5), .D(\Queue[4][22] ), 
        .Z(n186) );
  OAI21M1P U356 ( .A(n187), .B(n11), .C(n186), .Z(n530) );
  AOI22M1P U357 ( .A(\Queue[3][23] ), .B(n205), .C(n5), .D(\Queue[4][23] ), 
        .Z(n188) );
  OAI21M1P U358 ( .A(n189), .B(n11), .C(n188), .Z(n531) );
  AOI22M1P U359 ( .A(\Queue[3][24] ), .B(n205), .C(n5), .D(\Queue[4][24] ), 
        .Z(n190) );
  OAI21M1P U360 ( .A(n191), .B(n11), .C(n190), .Z(n532) );
  AOI22M1P U361 ( .A(\Queue[3][25] ), .B(n205), .C(n5), .D(\Queue[4][25] ), 
        .Z(n192) );
  OAI21M1P U362 ( .A(n193), .B(n11), .C(n192), .Z(n533) );
  AOI22M1P U363 ( .A(\Queue[3][26] ), .B(n205), .C(n5), .D(\Queue[4][26] ), 
        .Z(n194) );
  OAI21M1P U364 ( .A(n195), .B(n11), .C(n194), .Z(n534) );
  AOI22M1P U365 ( .A(\Queue[3][27] ), .B(n205), .C(n5), .D(\Queue[4][27] ), 
        .Z(n196) );
  OAI21M1P U366 ( .A(n197), .B(n11), .C(n196), .Z(n535) );
  AOI22M1P U367 ( .A(\Queue[3][28] ), .B(n205), .C(n5), .D(\Queue[4][28] ), 
        .Z(n198) );
  OAI21M1P U368 ( .A(n199), .B(n11), .C(n198), .Z(n536) );
  AOI22M1P U369 ( .A(\Queue[3][29] ), .B(n205), .C(n5), .D(\Queue[4][29] ), 
        .Z(n200) );
  OAI21M1P U370 ( .A(n201), .B(n11), .C(n200), .Z(n537) );
  AOI22M1P U371 ( .A(\Queue[3][30] ), .B(n205), .C(n5), .D(\Queue[4][30] ), 
        .Z(n202) );
  OAI21M1P U372 ( .A(n203), .B(n10), .C(n202), .Z(n538) );
  AOI22M1P U373 ( .A(\Queue[3][31] ), .B(n205), .C(n5), .D(\Queue[4][31] ), 
        .Z(n206) );
  OAI21M1P U374 ( .A(n208), .B(n11), .C(n206), .Z(n539) );
  N1M1P U375 ( .A(n540), .Z(Empty) );
  AOI22M1P U376 ( .A(n375), .B(n374), .C(n373), .D(Empty), .Z(n541) );
endmodule


module Link_Control_synth ( Clk_r, Rst_n, Tx_Ready, Tx_Error, RxData_Valid, RxData, 
        Packet_From_Core, TxQueue_Write, RxQueue_Read, TxData_Valid, TxData, 
        Error_Ack, Core_Rcv_Ready, Packet_To_Core, RxQueue_Empty, TxQueue_Full
 );
  input [31:0] RxData;
  input [31:0] Packet_From_Core;
  output [31:0] TxData;
  output [31:0] Packet_To_Core;
  input Clk_r, Rst_n, Tx_Ready, Tx_Error, RxData_Valid, TxQueue_Write,
         RxQueue_Read;
  output TxData_Valid, Error_Ack, Core_Rcv_Ready, RxQueue_Empty, TxQueue_Full;
  wire   TxQueue_Read, RxQueue_Write, RxQueue_Full, N9, N16, n1, n2, n3, n4,
         n9, n10, n11, n12, n13;

  Packet_Queue_0 TxQueue ( .Clk_r(Clk_r), .Rst_n(Rst_n), .Packet_In(
        Packet_From_Core), .Read_Ack(TxQueue_Read), .Write_Ack(TxQueue_Write), 
        .Packet_Out(TxData), .Empty(n10), .Full(TxQueue_Full) );
  Packet_Queue_1 RxQueue ( .Clk_r(Clk_r), .Rst_n(Rst_n), .Packet_In(RxData), 
        .Read_Ack(RxQueue_Read), .Write_Ack(RxQueue_Write), .Packet_Out(
        Packet_To_Core), .Empty(RxQueue_Empty), .Full(RxQueue_Full) );
  FD2QM1P TxQueue_Read_reg ( .D(N16), .CP(Clk_r), .CD(Rst_n), .Q(TxQueue_Read)
         );
  FD2QM1P Error_Ack_reg ( .D(Tx_Error), .CP(Clk_r), .CD(Rst_n), .Q(Error_Ack)
         );
  FD4QM3P RxQueue_Write_reg ( .D(n3), .CP(Clk_r), .SD(Rst_n), .Q(n4) );
  FD4QM8P TxData_Valid_reg ( .D(n1), .CP(Clk_r), .SD(Rst_n), .Q(n2) );
  FD2QM8P Core_Rcv_Ready_reg ( .D(N9), .CP(Clk_r), .CD(Rst_n), .Q(
        Core_Rcv_Ready) );
  ND2M2P U15 ( .A(n9), .B(Tx_Ready), .Z(n1) );
  ND2M3P U16 ( .A(n10), .B(n2), .Z(n9) );
  N1M4P U17 ( .A(n4), .Z(RxQueue_Write) );
  AOI21M3P U18 ( .A(RxQueue_Full), .B(n13), .C(n12), .Z(N9) );
  OAI21M1P U19 ( .A(Core_Rcv_Ready), .B(n11), .C(n4), .Z(n12) );
  N1M1P U20 ( .A(RxData_Valid), .Z(n11) );
  N1M1P U21 ( .A(Core_Rcv_Ready), .Z(n13) );
  NR2M1P U22 ( .A(n2), .B(Tx_Ready), .Z(N16) );
  N1M1P U23 ( .A(n2), .Z(TxData_Valid) );
  ND3M1P U24 ( .A(n4), .B(Core_Rcv_Ready), .C(RxData_Valid), .Z(n3) );
endmodule

