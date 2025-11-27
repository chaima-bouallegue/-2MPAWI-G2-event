terraform {
  required_version = ">= 1.0.0"

  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }
}

# --- AWS Provider ---
provider "aws" {
  region  = var.aws_region
  profile = var.aws_profile      # utilise ton fichier ~/.aws/credentials
}

# --- S3 Bucket ---
resource "aws_s3_bucket" "event_bucket" {
  bucket = var.bucket_name

  tags = {
    Name    = "EventBucket"
    Project = "-2MPAWI-G2-EVENT"
  }
}

# --- EC2 Instance ---
resource "aws_instance" "event_instance" {
  ami           = "ami-0c02fb55956c7d316" # Amazon Linux 2 (us-east-1)
  instance_type = var.instance_type

  tags = {
    Name    = "EventAppInstance"
    Project = "-2MPAWI-G2-EVENT"
  }
}
