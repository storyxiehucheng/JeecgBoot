import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '扣分项名称',
    align:"center",
    dataIndex: 'title'
   },
   {
    title: '扣分项描述',
    align:"center",
    dataIndex: 'description'
   },
   {
    title: '扣分数',
    align:"center",
    dataIndex: 'points'
   },
   {
    title: '图标',
    align:"center",
    dataIndex: 'icon',
    customRender:render.renderImage,
   },
   {
    title: '是否启用',
    align:"center",
    dataIndex: 'status',
    customRender:({text}) => {
       return  render.renderSwitch(text, [{text:'是',value:'Y'},{text:'否',value:'N'}])
     },
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '扣分项名称',
    field: 'title',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入扣分项名称!'},
          ];
     },
  },
  {
    label: '扣分项描述',
    field: 'description',
    component: 'InputTextArea',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入扣分项描述!'},
          ];
     },
  },
  {
    label: '扣分数',
    field: 'points',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入扣分数!'},
          ];
     },
  },
  {
    label: '图标',
    field: 'icon',
     component: 'JImageUpload',
     componentProps:{
        fileMax: 0
      },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入图标!'},
          ];
     },
  },
  {
    label: '是否启用',
    field: 'status',
    defaultValue: "Y",
     component: 'JSwitch',
     componentProps:{
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入是否启用!'},
          ];
     },
  },
	// TODO 主键隐藏字段，目前写死为ID
	{
	  label: '',
	  field: 'id',
	  component: 'Input',
	  show: false
	},
];

// 高级查询数据
export const superQuerySchema = {
  title: {title: '扣分项名称',order: 0,view: 'text', type: 'string',},
  description: {title: '扣分项描述',order: 1,view: 'textarea', type: 'string',},
  points: {title: '扣分数',order: 2,view: 'number', type: 'number',},
  icon: {title: '图标',order: 3,view: 'image', type: 'string',},
  status: {title: '是否启用',order: 4,view: 'switch', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}