#!/s/std/bin/perl

use strict;

my $total_errors=0;

my @files = `/bin/ls -c1 *.v`;
chomp @files;
my $nfiles = $#files;
foreach my $a (@files) {
  print "Checking file $a: ";
  &check_file($a);
}

print "Examined $nfiles files, Found $total_errors errors\n";

sub check_file() {

  
  my $fname = shift(@_);

  open(F1, $fname);
  my @lines=<F1>;
  close F1;

  my $n_modules=0;
  my $i=1;
  foreach my $a (@lines) {
    if ($a =~ /^module\s+(\S+)\s*\(/) {
      my $module_name = $1;
      my $expected_module_name = $fname;
      $expected_module_name =~ s/.v$//g;

      if ($expected_module_name eq $module_name) {
        print "OK: ";
      } else {
        print "ERROR: (name mismatch) ";
        $total_errors++;
      }
      print "Found module $module_name (expected $expected_module_name) in $fname, at line $i:$a\n";
      $n_modules++;
    }
    $i++;
  }

  if ($n_modules > 1) {
    print "ERROR: (found more than one module in a single file)\n";
    $total_errors++;
  }
  if ($n_modules <= 0) {
    print "ERROR: (no modules found in file)\n";
    $total_errors++;
  }
  
}

