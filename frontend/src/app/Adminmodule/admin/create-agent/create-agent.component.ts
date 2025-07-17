import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule,Validators } from '@angular/forms';
import { AdminAgentService } from '../services/agent.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NgIf } from '@angular/common';
import 'bootstrap/dist/css/bootstrap.min.css';
import { NavbarComponent } from '../../../components/navbar/navbar.component';


@Component({
  selector: 'app-create-agent',
  imports: [ReactiveFormsModule,NgIf,NavbarComponent],
  templateUrl: './create-agent.component.html',
  styleUrl: './create-agent.component.css'
})
export class AdminCreateAgentComponent implements OnInit {

  createAgentForm:FormGroup=new FormGroup({});
  constructor(private formBuilder:FormBuilder,private activatedRoute:ActivatedRoute,private  agentService:AdminAgentService,private router:Router){}
  ngOnInit(): void {
      this.createAgentForm = this.formBuilder.group({
        name: ['',Validators.required],
        contactNumber: ['',[Validators.required]],
      })
    }
     
  
    onSubmit(){
    
          let agent=this.createAgentForm.value
          
          let responseMessage=""
          console.log(agent);
          this.agentService.createAgent(agent).subscribe({
              next:(response)=>{    
                alert("Agent added successfully")
                this.router.navigate(['agent'])
              }
          })
  
        }
      }
  
    
    
  
  