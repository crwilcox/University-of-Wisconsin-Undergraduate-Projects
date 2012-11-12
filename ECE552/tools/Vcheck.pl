#! /usr/bin/perl
use warnings;
use strict;
use Cwd;
use FindBin qw($Bin); #directory script was in
my @dirStack,
my %novcheck;
die "usage: Vcheck.pl DIR_TO_CHECK_RECURSIVELY" if (@ARGV != 1);
chdir($ARGV[0]) or die "Couldnt change to $ARGV[0]  $!";
#read in file
open NOVCHECK, "<$Bin/no_vcheck.txt" or die "could not open the resource file no_vcheck.txt, is it in the same dir as the script? $!";
while (<NOVCHECK>) {
	chomp;
	$novcheck{$_} = 1 unless /^#/;
}
close(NOVCHECK);
#print("$_ -> $novcheck{$_}\n") foreach (keys %novcheck);
&recursive_vcheck(getcwd);

#first arg is a directory to check files in
sub recursive_vcheck{
	my @dirs;
	my $file;	
	print("Recursing into all directories of $_[0]\n");
	#get all directories in this directory
	foreach $file (<*>){
		#print ("$file\n");
		push @dirs,$file if -d $file;
	}	
		foreach (@dirs){	
			push @dirStack,$_[0];	
			chdir($_);
			&recursive_vcheck(getcwd); 
			chdir(pop @dirStack);
		}
		#will get here when no more subdirectories
		#print ("checking files in $_[0]\n");	
		#now check the files of this directory
		foreach (<*.v>){
			if ($novcheck{$_}) {
				#skip it
				print("\nSkipping file : $_\n");	
			}
			else {
				print("\nVcheck on: $_\n");	
				system("java -cp $Bin Vcheck $_");
			}
		}
}

