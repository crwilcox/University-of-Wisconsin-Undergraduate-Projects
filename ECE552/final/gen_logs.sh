#!/bin/bash

rm -f inst_tests.summary.log
wsrun.pl -args -novopt -pipe -align -list /afs/cs.wisc.edu/p/course/cs552-david/public/html/S10/project/tests/public/inst_tests/all.list proc_hier_pbench *.v
mv summary.log inst_tests.summary.log

rm -f complex_demo1.summary.log
wsrun.pl -args -novopt -pipe -align -list /afs/cs.wisc.edu/p/course/cs552-david/public/html/S10/project/tests/public/complex_demo1/all.list proc_hier_pbench *.v
mv summary.log complex_demo1.summary.log

rm -f complex_demo2.summary.log
wsrun.pl -args -novopt -pipe -align -list /afs/cs.wisc.edu/p/course/cs552-david/public/html/S10/project/tests/public/complex_demo2/all.list proc_hier_pbench *.v
mv summary.log complex_demo2.summary.log

rm -f complex_demofinal.summary.log
wsrun.pl -args -novopt -pipe -align -list /afs/cs.wisc.edu/p/course/cs552-david/public/html/S10/project/tests/public/complex_demofinal/all.list proc_hier_pbench *.v
mv summary.log complex_demofinal.summary.log

rm -f rand_simple.summary.log
wsrun.pl -args -novopt -pipe -align -list /afs/cs.wisc.edu/p/course/cs552-david/public/html/S10/project/tests/public/rand_simple/all.list proc_hier_pbench *.v
mv summary.log rand_simple.summary.log

rm -f rand_complex.summary.log
wsrun.pl -args -novopt -pipe -align -list /afs/cs.wisc.edu/p/course/cs552-david/public/html/S10/project/tests/public/rand_complex/all.list proc_hier_pbench *.v
mv summary.log rand_complex.summary.log

rm -f rand_ctrl.summary.log
wsrun.pl -args -novopt -pipe -align -list /afs/cs.wisc.edu/p/course/cs552-david/public/html/S10/project/tests/public/rand_ctrl/all.list proc_hier_pbench *.v 
mv summary.log rand_ctrl.summary.log

rm -f rand_final.summary.log
wsrun.pl -args -novopt -pipe -align -list /afs/cs.wisc.edu/p/course/cs552-david/public/html/S10/project/tests/public/rand_final/all.list proc_hier_pbench *.v
mv summary.log rand_final.summary.log

rm -f rand_mem.summary.log
wsrun.pl -args -novopt -pipe -align -list /afs/cs.wisc.edu/p/course/cs552-david/public/html/S10/project/tests/public/rand_mem/all.list proc_hier_pbench *.v
mv summary.log rand_mem.summary.log

rm -f rand_final.summary.log
wsrun.pl -args -novopt -pipe -align -list /afs/cs.wisc.edu/p/course/cs552-david/public/html/S10/project/tests/public/rand_final/all.list proc_hier_pbench *.v
mv summary.log rand_final.summary.log

rm -f rand_dcache.summary.log
wsrun.pl -args -novopt -pipe -align -list /afs/cs.wisc.edu/p/course/cs552-david/public/html/S10/project/tests/public/rand_dcache/all.list proc_hier_pbench *.v
mv summary.log rand_dcache.summary.log

rm -f rand_icache.summary.log
wsrun.pl -args -novopt -pipe -align -list /afs/cs.wisc.edu/p/course/cs552-david/public/html/S10/project/tests/public/rand_icache/all.list proc_hier_pbench *.v
mv summary.log rand_icache.summary.log

rm -f rand_idcache.summary.log
wsrun.pl -args -novopt -pipe -align -list /afs/cs.wisc.edu/p/course/cs552-david/public/html/S10/project/tests/public/rand_idcache/all.list proc_hier_pbench *.v
mv summary.log rand_idcache.summary.log

rm -f rand_ldst.summary.log
wsrun.pl -args -novopt -pipe -align -list /afs/cs.wisc.edu/p/course/cs552-david/public/html/S10/project/tests/public/rand_ldst/all.list proc_hier_pbench *.v
mv summary.log rand_ldst.summary.log
